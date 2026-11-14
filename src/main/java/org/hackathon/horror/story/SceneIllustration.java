package org.hackathon.horror.story;

import javax.swing.*;
import java.awt.*;

public class SceneIllustration extends JPanel {
    private final OutdoorScenePainter outdoorPainter = new OutdoorScenePainter();
    private final IndoorScenePainter indoorPainter = new IndoorScenePainter();
    private int personX = 45;
    private int step;
    private Timer timer;
    private Scene scene = Scene.START;

    public SceneIllustration() {
        setPreferredSize(new Dimension(500, 190));
        setBackground(new Color(25, 30, 45));
    }

    public void showScene(Scene scene) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        this.scene = scene;
        personX = 45;
        step = 0;

        if (isAnimatedScene(scene)) {
            int speed = scene == Scene.RUN || scene == Scene.KEEP_RUNNING
                    || scene == Scene.KEEP_SPRINTING || scene == Scene.FOREST_DASH
                    || scene == Scene.FOREST_BREAK_RUN ? 7 : 2;
            timer = new Timer(40, event -> {
                personX += speed;
                step++;
                if (personX > getWidth() - 45) {
                    personX = 45;
                }
                repaint();
            });
            timer.start();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (isOutdoorScene(scene)) {
            outdoorPainter.paint(g, getWidth(), getHeight(), scene);
        } else {
            indoorPainter.paint(g, getWidth(), getHeight(), scene, personX);
        }

        if (scene == Scene.RUN || scene == Scene.KEEP_RUNNING
                || scene == Scene.KEEP_SPRINTING || scene == Scene.WALK) {
            drawPerson(g, false);
        } else if (scene == Scene.DARK_ROOM_WITH_LAMP) {
            drawPerson(g, true);
        } else if (scene == Scene.FOREST_DASH || scene == Scene.FOREST_BREAK_RUN) {
            drawPerson(g, false);
        }
        g.dispose();
    }

    private boolean isAnimatedScene(Scene scene) {
        return scene == Scene.RUN || scene == Scene.KEEP_RUNNING || scene == Scene.KEEP_SPRINTING
                || scene == Scene.WALK || scene == Scene.DARK_ROOM_WITH_LAMP
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
        int groundY = getHeight() - 30;
        int bodyY = groundY - 55;
        int stride = step % 2 == 0 ? 8 : -8;
        g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(235, 235, 240));
        g.drawOval(personX - 8, bodyY - 20, 16, 16);
        g.drawLine(personX, bodyY - 4, personX, bodyY + 22);
        g.drawLine(personX, bodyY + 3, personX - 12, bodyY + 15);
        if (holdingLamp) {
            int lampX = personX + 16;
            int lampY = bodyY + 10;
            g.drawLine(personX, bodyY + 3, lampX, lampY);
            g.setColor(new Color(255, 244, 190));
            g.fillOval(lampX - 5, lampY - 5, 10, 10);
        } else {
            g.drawLine(personX, bodyY + 3, personX + 12, bodyY + 15);
        }
        g.drawLine(personX, bodyY + 22, personX - 10 + stride, groundY);
        g.drawLine(personX, bodyY + 22, personX + 10 - stride, groundY);
    }
}
