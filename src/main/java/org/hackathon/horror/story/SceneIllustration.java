package org.hackathon.horror.story;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;

public class SceneIllustration extends JPanel {
    private final OutdoorScenePainter outdoorPainter = new OutdoorScenePainter();
    private final IndoorScenePainter indoorPainter = new IndoorScenePainter();
    private final Timer timer;
    private double personX = 52;
    private double animationPhase;
    private int travelDirection = 1;
    private long lastFrameNanos;
    private Scene scene = Scene.START;

    public SceneIllustration() {
        setPreferredSize(new Dimension(500, 190));
        setBackground(new Color(13, 12, 11));
        timer = new Timer(16, event -> advanceAnimation());
        timer.setCoalesce(true);
        addHierarchyListener(event -> {
            if ((event.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                synchronizeAnimationTimer();
            }
        });
    }

    public void showScene(Scene scene) {
        stopAnimationTimer();
        this.scene = scene;
        personX = leftMovementLimit();
        animationPhase = 0;
        travelDirection = 1;
        synchronizeAnimationTimer();
        repaint();
    }

    @Override
    public void removeNotify() {
        stopAnimationTimer();
        super.removeNotify();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            if (isOutdoorScene(scene)) {
                outdoorPainter.paint(g, getWidth(), getHeight(), scene);
            } else {
                indoorPainter.paint(g, getWidth(), getHeight(), scene, (int) Math.round(personX));
            }

            if (scene == Scene.RUN || scene == Scene.KEEP_RUNNING
                    || scene == Scene.KEEP_SPRINTING || scene == Scene.WALK
                    || scene == Scene.FOREST_DASH || scene == Scene.FOREST_BREAK_RUN) {
                drawPerson(g, false);
            } else if (scene == Scene.DARK_ROOM_WITH_LAMP) {
                drawPerson(g, true);
            }
        } finally {
            g.dispose();
        }
    }

    private void synchronizeAnimationTimer() {
        if (isShowing() && isAnimatedScene(scene)) {
            if (!timer.isRunning()) {
                clampPersonPosition();
                lastFrameNanos = System.nanoTime();
                timer.start();
            }
        } else {
            stopAnimationTimer();
        }
    }

    private void stopAnimationTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        lastFrameNanos = 0;
    }

    private void advanceAnimation() {
        if (!isShowing() || !isAnimatedScene(scene)) {
            synchronizeAnimationTimer();
            return;
        }

        long now = System.nanoTime();
        double elapsedSeconds = Math.min((now - lastFrameNanos) / 1_000_000_000.0, 0.05);
        lastFrameNanos = now;

        double minimumX = leftMovementLimit();
        double maximumX = rightMovementLimit();
        clampPersonPosition();

        double speed = isRunningScene(scene) ? 180 : 55;
        personX += travelDirection * speed * elapsedSeconds;
        if (personX >= maximumX) {
            personX = maximumX;
            travelDirection = -1;
        } else if (personX <= minimumX) {
            personX = minimumX;
            travelDirection = 1;
        }

        double stepsPerSecond = isRunningScene(scene) ? 3.2 : 1.7;
        animationPhase = (animationPhase + Math.PI * 2 * stepsPerSecond * elapsedSeconds)
                % (Math.PI * 2);
        repaint();
    }

    private void clampPersonPosition() {
        double minimumX = leftMovementLimit();
        double maximumX = rightMovementLimit();
        personX = Math.max(minimumX, Math.min(personX, maximumX));
        if (personX == minimumX) {
            travelDirection = 1;
        } else if (personX == maximumX) {
            travelDirection = -1;
        }
    }

    private int leftMovementLimit() {
        return scene == Scene.DARK_ROOM_WITH_LAMP ? 50 : 24;
    }

    private int rightMovementLimit() {
        int rightMargin = scene == Scene.DARK_ROOM_WITH_LAMP ? 82 : 28;
        return Math.max(leftMovementLimit(), getWidth() - rightMargin);
    }

    private boolean isAnimatedScene(Scene scene) {
        return scene == Scene.RUN || scene == Scene.KEEP_RUNNING || scene == Scene.KEEP_SPRINTING
                || scene == Scene.WALK || scene == Scene.DARK_ROOM_WITH_LAMP
                || scene == Scene.FOREST_DASH || scene == Scene.FOREST_BREAK_RUN;
    }

    private boolean isRunningScene(Scene scene) {
        return scene == Scene.RUN || scene == Scene.KEEP_RUNNING || scene == Scene.KEEP_SPRINTING
                || scene == Scene.FOREST_DASH || scene == Scene.FOREST_BREAK_RUN;
    }

    private boolean isOutdoorScene(Scene scene) {
        switch (scene) {
            case START:
            case GATES:
            case WOODS_PATH_CHOICE:
            case LEFT_ROAD:
            case RUN:
            case KEEP_RUNNING:
            case WALK:
            case HATCHET_FIGHT:
            case KEEP_SPRINTING:
            case FOREST_DASH:
            case FOREST_LEAVE_NOW:
            case FOREST_WAIT:
            case FOREST_LEAVE_QUIETLY:
            case FOREST_BREAK_RUN:
            case CROSS_STREET:
                return true;
            default:
                return false;
        }
    }

    private void drawPerson(Graphics2D g, boolean holdingLamp) {
        int x = (int) Math.round(personX);
        int groundY = getHeight() - 30;
        int bob = (int) Math.round(Math.abs(Math.sin(animationPhase)) * 2);
        int bodyY = groundY - 55 + bob;
        int stride = (int) Math.round(Math.sin(animationPhase) * 9);
        g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(235, 235, 240));
        g.drawOval(x - 8, bodyY - 20, 16, 16);
        g.drawLine(x, bodyY - 4, x, bodyY + 22);
        g.drawLine(x, bodyY + 3, x - 12, bodyY + 15 - stride);
        if (holdingLamp) {
            int lampX = x + 16;
            int lampY = bodyY + 10;
            g.drawLine(x, bodyY + 3, lampX, lampY);
            g.setColor(new Color(255, 244, 190));
            g.fillOval(lampX - 5, lampY - 5, 10, 10);
        } else {
            g.drawLine(x, bodyY + 3, x + 12, bodyY + 15 + stride);
        }
        g.drawLine(x, bodyY + 22, x - 10 + stride, groundY);
        g.drawLine(x, bodyY + 22, x + 10 - stride, groundY);
    }
}
