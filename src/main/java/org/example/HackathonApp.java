package org.example;

import javax.swing.*;
import java.awt.*;

public class HackathonApp {
    private final JFrame frame = new JFrame("Lost on the Trail");
    private final JTextArea storyText = new JTextArea();
    private final JPanel choices = new JPanel(new FlowLayout());
    private final MovementPanel movementArea = new MovementPanel();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HackathonApp().start());
    }

    private void start() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        storyText.setEditable(false);
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setFont(new Font("SansSerif", Font.PLAIN, 16));
        storyText.setMargin(new Insets(16, 16, 16, 16));

        JPanel scenePanel = new JPanel(new BorderLayout());

        scenePanel.add(storyText, BorderLayout.NORTH);
        scenePanel.add(movementArea, BorderLayout.CENTER);

        frame.add(scenePanel, BorderLayout.CENTER);
        frame.add(choices, BorderLayout.SOUTH);

        showScene(Scene.START);
        frame.setVisible(true);
    }

    private void showScene(Scene scene) {
        choices.removeAll();
        movementArea.showScene(scene);

        switch (scene) {
            case START:
                setStory("You're lost on a trail, with only the wind to accompany you. "
                        + "You walk forward until the trail splits. Which path do you take?");
                addChoice("Go left", Scene.LEFT_ROAD);
                addChoice("Go right", Scene.GATES);
                break;
            case GATES:
                setStory("You take the right path and find yourself at the foot of large iron gates. "
                        + "Do you push them open or turn around and try the other path?");
                addChoice("Push open the gates", Scene.MANOR_DOOR);
                addChoice("Turn around and go left", Scene.LEFT_ROAD);
                break;
            case MANOR_DOOR:
                setStory("Beyond the gates is a manor covered in spiderwebs. Rats scratch inside the walls. "
                        + "You climb the steps to an old wooden door. Do you slowly open it?");
                addChoice("Yes, open the door", Scene.ENTER_MANOR);
                addChoice("No, leave the door closed", Scene.LEAVE_MANOR);
                break;
            case ENTER_MANOR:
                setStory("You slowly open the door, peeking inside before entering the manor. "
                        + "A cold draft brushes past you. Your adventure continues...");
                addChoice("Start over", Scene.START);
                break;
            case LEAVE_MANOR:
                setStory("You decide not to open the door. As you step away, the door creaks open behind you...");
                addChoice("Start over", Scene.START);
                break;
            case LEFT_ROAD:
                setStory("You take the left path and reach an empty street. The sun disappears behind "
                        + "the horizon. Headlights approach. Do you start running?");
                addChoice("Run", Scene.RUN);
                addChoice("Keep walking", Scene.WALK);
                break;
            case RUN:
                setStory("You pick up your pace, then break into a run down the road. The headlights follow...");
                movementArea.startAnimation(true);
                addChoice("Start over", Scene.START);
                break;
            case WALK:
                setStory("You continue walking calmly as the car approaches. It slows beside you...");
                movementArea.startAnimation(false);
                addChoice("Start over", Scene.START);
                break;
        }

        choices.revalidate();
        choices.repaint();
    }

    private void setStory(String text) {
        storyText.setText(text);
        storyText.setCaretPosition(0);
    }

    private void addChoice(String label, Scene nextScene) {
        JButton button = new JButton(label);
        button.addActionListener(event -> showScene(nextScene));
        choices.add(button);
    }

    private static class MovementPanel extends JPanel {
        private int personX = 45;
        private int step = 0;
        private Timer timer;
        private boolean running;
        private Scene scene = Scene.START;

        private MovementPanel() {
            setPreferredSize(new Dimension(500, 190));
            setBackground(new Color(25, 30, 45));
        }

        private void startAnimation(boolean running) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }

            scene = running ? Scene.RUN : Scene.WALK;
            this.running = running;
            personX = 45;
            step = 0;
            int speed = running ? 7 : 2;

            timer = new Timer(40, event -> {
                personX += speed;
                step++;
                if (personX > getWidth() - 45) {
                    personX = 45;
                }
                repaint();
            });
            timer.start();
            repaint();
        }

        private void showScene(Scene scene) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            this.scene = scene;
            running = false;
            personX = 45;
            step = 0;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            switch (scene) {
                case START:
                    drawTrail(g);
                    break;
                case GATES:
                    drawGates(g);
                    break;
                case MANOR_DOOR:
                case ENTER_MANOR:
                case LEAVE_MANOR:
                    drawManor(g, scene == Scene.ENTER_MANOR);
                    break;
                case LEFT_ROAD:
                case RUN:
                case WALK:
                    drawRoad(g);
                    break;
            }

            if (scene == Scene.RUN || scene == Scene.WALK) {
                drawPerson(g);
            }

            g.dispose();
        }

        private void drawTrail(Graphics2D g) {
            setBackground(new Color(19, 35, 33));
            g.setColor(new Color(29, 55, 45));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(54, 74, 57));
            g.fillOval(-60, -35, 190, 160);
            g.fillOval(20, 15, 150, 140);
            g.fillOval(getWidth() - 150, -30, 200, 180);
            g.setColor(new Color(110, 93, 68));
            Polygon path = new Polygon(
                    new int[]{getWidth() / 2 - 25, getWidth() / 2 + 25, getWidth() + 40, -40},
                    new int[]{getHeight() / 2, getHeight() / 2, getHeight(), getHeight()},
                    4);
            g.fillPolygon(path);
            g.setColor(new Color(142, 131, 105));
            g.drawLine(getWidth() / 2, getHeight() / 2 + 10, getWidth() / 2, getHeight());
            g.setColor(new Color(190, 200, 195));
            g.drawString("THE TRAIL", 15, 22);
        }

        private void drawGates(Graphics2D g) {
            setBackground(new Color(22, 30, 37));
            g.setColor(new Color(30, 42, 48));
            g.fillRect(0, 0, getWidth(), getHeight());
            int gateWidth = Math.min(250, getWidth() - 70);
            int left = (getWidth() - gateWidth) / 2;
            int top = 25;
            int bottom = getHeight() - 25;
            g.setColor(new Color(95, 91, 82));
            g.fillRect(left - 14, top - 8, 14, bottom - top + 8);
            g.fillRect(left + gateWidth, top - 8, 14, bottom - top + 8);
            g.setColor(new Color(18, 21, 24));
            g.fillRect(left, top, gateWidth, bottom - top);
            g.setColor(new Color(125, 128, 125));
            g.setStroke(new BasicStroke(4));
            g.drawRect(left, top, gateWidth, bottom - top);
            for (int x = left + 18; x < left + gateWidth; x += 28) {
                g.drawLine(x, top + 3, x, bottom);
                g.fillPolygon(
                        new int[]{x - 6, x, x + 6},
                        new int[]{top + 8, top - 2, top + 8},
                        3);
            }
            g.drawLine(left, top + 45, left + gateWidth, top + 45);
            g.drawLine(left, bottom - 35, left + gateWidth, bottom - 35);
            g.setColor(new Color(176, 176, 158));
            g.drawString("IRON GATES", left + gateWidth / 2 - 33, getHeight() - 8);
        }

        private void drawManor(Graphics2D g, boolean doorOpen) {
            setBackground(new Color(18, 22, 33));
            g.setColor(new Color(18, 22, 33));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(42, 43, 48));
            g.fillRect(100, 65, getWidth() - 200, getHeight() - 65);
            g.setColor(new Color(28, 29, 36));
            g.fillPolygon(
                    new int[]{75, getWidth() / 2, getWidth() - 75},
                    new int[]{70, 18, 70},
                    3);

            g.setColor(new Color(182, 170, 112));
            g.fillRect(145, 85, 35, 35);
            g.fillRect(getWidth() - 180, 85, 35, 35);
            g.setColor(new Color(31, 33, 40));
            g.drawLine(162, 85, 162, 120);
            g.drawLine(145, 102, 180, 102);
            g.drawLine(getWidth() - 163, 85, getWidth() - 163, 120);
            g.drawLine(getWidth() - 180, 102, getWidth() - 145, 102);

            int doorX = getWidth() / 2 - 28;
            int doorY = getHeight() - 92;
            g.setColor(new Color(94, 64, 42));
            g.fillRect(doorX, doorY, 56, 92);
            if (doorOpen) {
                g.setColor(new Color(8, 9, 15));
                g.fillPolygon(
                        new int[]{doorX, doorX + 56, doorX + 82, doorX + 26},
                        new int[]{doorY, doorY, doorY + 92, doorY + 92},
                        4);
                g.setColor(new Color(122, 85, 51));
                g.drawLine(doorX, doorY, doorX + 26, doorY + 92);
            } else {
                g.setColor(new Color(48, 34, 28));
                g.drawRect(doorX, doorY, 56, 92);
                g.drawLine(doorX + 28, doorY + 5, doorX + 28, doorY + 88);
                g.setColor(new Color(205, 177, 97));
                g.fillOval(doorX + 44, doorY + 48, 5, 5);
            }

            g.setColor(new Color(112, 111, 108));
            g.fillRect(getWidth() / 2 - 65, getHeight() - 18, 130, 18);
            g.setColor(new Color(205, 205, 195));
            g.drawString("THE OLD MANOR", getWidth() / 2 - 43, 15);
        }

        private void drawRoad(Graphics2D g) {
            setBackground(new Color(19, 24, 37));
            g.setColor(new Color(19, 24, 37));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(47, 49, 56));
            g.fillRect(0, getHeight() - 48, getWidth(), 48);
            g.setColor(new Color(209, 195, 139));
            for (int x = 15; x < getWidth(); x += 85) {
                g.fillRect(x, getHeight() - 27, 42, 3);
            }
            g.setColor(new Color(255, 224, 153));
            g.fillOval(getWidth() - 68, getHeight() - 83, 30, 18);
            g.setColor(new Color(255, 232, 173, 45));
            g.fillOval(getWidth() - 132, getHeight() - 100, 90, 50);
            g.setColor(new Color(205, 210, 218));
            g.drawString("EMPTY ROAD", 14, 22);
        }

        private void drawPerson(Graphics2D g) {
            int groundY = getHeight() - 30;
            int bodyY = groundY - 55;
            int stride = (step % 2 == 0) ? 8 : -8;
            g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(new Color(235, 235, 240));
            g.drawOval(personX - 8, bodyY - 20, 16, 16);
            g.drawLine(personX, bodyY - 4, personX, bodyY + 22);
            g.drawLine(personX, bodyY + 3, personX - 12, bodyY + 15);
            g.drawLine(personX, bodyY + 3, personX + 12, bodyY + 15);
            g.drawLine(personX, bodyY + 22, personX - 10 + stride, groundY);
            g.drawLine(personX, bodyY + 22, personX + 10 - stride, groundY);
        }
    }

    private enum Scene {
        START,
        GATES,
        MANOR_DOOR,
        ENTER_MANOR,
        LEAVE_MANOR,
        LEFT_ROAD,
        RUN,
        WALK
    }
}
