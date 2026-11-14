package org.hackathon.horror.story;

import java.awt.*;

final class IndoorScenePainter {
    void paint(Graphics2D g, int width, int height, Scene scene, int personX) {
        switch (scene) {
            case MANOR_DOOR:
            case LEAVE_MANOR:
                drawManor(g, width, height);
                break;
            case DOOR_KICKED:
            case ENTER_MANOR:
                drawChamber(g, width, height);
                break;
            case DARK_ROOM_WITH_LAMP:
                drawDarkRoom(g, width, height, true, false, personX);
                break;
            case DARK_ROOM_WITHOUT_LAMP:
                drawDarkRoom(g, width, height, false, false, personX);
                break;
            case STEP_OVER_SHARDS:
            case WALK_AROUND_SHARDS:
                drawDarkRoom(g, width, height, false, true, personX);
                break;
            case DIM_ROOM:
                drawDimRoom(g, width, height, false);
                break;
            case BOXES_CHOICE:
                drawBoxes(g, width, height);
                break;
            case CELLAR:
                drawCellar(g, width, height);
                break;
            case VENT_DISCOVERY:
                drawDimRoom(g, width, height, true);
                break;
            case VENT_CREATURE_CHOICE:
                drawCreature(g, width, height, false);
                break;
            case CREATURE_ROOM:
            case GRAB_SECOND_OBJECT:
                drawCreature(g, width, height, true);
                break;
            case MINESHAFT_ENTER:
            case MINESHAFT_IGNORE:
                drawMineshaft(g, width, height);
                break;
            default:
                throw new IllegalArgumentException("Not an indoor scene: " + scene);
        }
    }

    private void drawManor(Graphics2D g, int width, int height) {
        fillBackground(g, width, height, new Color(18, 22, 33));
        g.setColor(new Color(42, 43, 48));
        g.fillRect(100, 65, width - 200, height - 65);
        g.setColor(new Color(28, 29, 36));
        g.fillPolygon(new int[]{75, width / 2, width - 75},
                new int[]{70, 18, 70}, 3);

        g.setColor(new Color(182, 170, 112));
        g.fillRect(145, 85, 35, 35);
        g.fillRect(width - 180, 85, 35, 35);
        g.setColor(new Color(31, 33, 40));
        g.drawLine(162, 85, 162, 120);
        g.drawLine(145, 102, 180, 102);
        g.drawLine(width - 163, 85, width - 163, 120);
        g.drawLine(width - 180, 102, width - 145, 102);

        int doorX = width / 2 - 28;
        int doorY = height - 92;
        g.setColor(new Color(94, 64, 42));
        g.fillRect(doorX, doorY, 56, 92);
        g.setColor(new Color(48, 34, 28));
        g.drawRect(doorX, doorY, 56, 92);
        g.drawLine(doorX + 28, doorY + 5, doorX + 28, doorY + 88);
        g.setColor(new Color(205, 177, 97));
        g.fillOval(doorX + 44, doorY + 48, 5, 5);
        g.setColor(new Color(112, 111, 108));
        g.fillRect(width / 2 - 65, height - 18, 130, 18);
        drawCaption(g, "THE OLD MANOR");
    }

    private void drawChamber(Graphics2D g, int width, int height) {
        fillBackground(g, width, height, new Color(16, 17, 29));
        g.setColor(new Color(48, 47, 57));
        g.fillRect(35, 25, width - 70, height - 25);
        g.setColor(new Color(69, 65, 72));
        for (int y = 55; y < height - 35; y += 42) {
            g.drawLine(35, y, width - 35, y);
        }
        for (int x = 70; x < width - 35; x += 75) {
            g.drawLine(x, 25, x, height - 35);
        }

        int orbX = width / 2 - 42;
        int orbY = height / 2 + 5;
        g.setColor(new Color(255, 215, 80, 35));
        g.fillOval(orbX - 40, orbY - 40, 100, 100);
        g.setColor(new Color(255, 218, 112, 90));
        g.fillOval(orbX - 20, orbY - 20, 60, 60);
        g.setColor(new Color(255, 244, 190));
        g.fillOval(orbX, orbY, 20, 20);
        drawCaption(g, "A GLOWING OBJECT");
    }

    private void drawDarkRoom(Graphics2D g, int width, int height,
                              boolean hasLamp, boolean hasShards, int personX) {
        fillBackground(g, width, height, Color.BLACK);
        if (hasLamp) {
            int lampX = personX + 16;
            int lampY = height - 30 - 47;
            g.setColor(new Color(255, 215, 80, 18));
            g.fillOval(lampX - 60, lampY - 60, 120, 120);
            g.setColor(new Color(255, 215, 80, 35));
            g.fillOval(lampX - 32, lampY - 32, 64, 64);
            g.setColor(new Color(255, 244, 190, 100));
            g.fillOval(lampX - 9, lampY - 9, 18, 18);
        }
        if (hasShards) {
            g.setColor(new Color(180, 190, 200));
            g.fillPolygon(new int[]{width / 2 - 35, width / 2 - 20, width / 2 - 12},
                    new int[]{height - 35, height - 68, height - 30}, 3);
            g.fillPolygon(new int[]{width / 2 - 7, width / 2 + 8, width / 2 + 20},
                    new int[]{height - 30, height - 61, height - 34}, 3);
            g.fillPolygon(new int[]{width / 2 + 24, width / 2 + 37, width / 2 + 45},
                    new int[]{height - 32, height - 54, height - 28}, 3);
        }
    }

    private void drawDimRoom(Graphics2D g, int width, int height, boolean ventVisible) {
        fillBackground(g, width, height, new Color(43, 42, 44));
        g.setColor(new Color(65, 62, 58));
        g.fillRect(45, 28, width - 90, height - 28);
        g.setColor(new Color(80, 77, 70));
        g.fillRect(100, 48, 82, 66);
        g.setColor(new Color(32, 33, 35));
        g.fillRect(107, 55, 68, 52);
        g.setColor(new Color(112, 108, 99));
        g.setStroke(new BasicStroke(5));
        g.drawLine(107, 55, 175, 107);
        g.drawLine(175, 55, 107, 107);
        g.drawLine(140, 55, 140, 107);
        g.drawLine(107, 81, 175, 81);

        g.setColor(new Color(85, 100, 120));
        g.fillOval(width - 135, 62, 5, 5);
        g.fillOval(width - 132, 68, 3, 10);
        if (ventVisible) {
            g.setColor(new Color(22, 23, 24));
            g.fillRect(width / 2 - 45, height - 46, 90, 28);
            g.setColor(new Color(120, 120, 115));
            g.drawRect(width / 2 - 45, height - 46, 90, 28);
            for (int x = width / 2 - 32; x < width / 2 + 40; x += 16) {
                g.drawLine(x, height - 43, x, height - 21);
            }
        }
        drawCaption(g, ventVisible ? "A DUSTY VENT" : "THE DIM ROOM");
    }

    private void drawBoxes(Graphics2D g, int width, int height) {
        drawDimRoom(g, width, height, false);
        g.setColor(new Color(93, 65, 43));
        g.fillRect(width / 2 - 55, height - 78, 105, 58);
        g.fillRect(width / 2 - 5, height - 115, 85, 40);
        g.setColor(new Color(140, 104, 68));
        g.drawRect(width / 2 - 55, height - 78, 105, 58);
        g.drawRect(width / 2 - 5, height - 115, 85, 40);
        g.drawLine(width / 2 - 2, height - 78, width / 2 - 2, height - 20);
        drawCaption(g, "A PILE OF BOXES");
    }

    private void drawCellar(Graphics2D g, int width, int height) {
        fillBackground(g, width, height, new Color(30, 27, 28));
        g.setColor(new Color(66, 52, 43));
        for (int x = 25; x < width; x += 55) {
            g.drawLine(x, 20, x, height - 15);
        }
        g.setColor(new Color(110, 106, 101));
        g.setStroke(new BasicStroke(4));
        int manX = width / 2;
        g.drawOval(manX - 10, 48, 20, 20);
        g.drawLine(manX, 68, manX, 130);
        g.drawLine(manX, 84, manX - 35, 95);
        g.drawLine(manX, 84, manX + 35, 95);
        g.drawLine(manX, 130, manX - 18, 165);
        g.drawLine(manX, 130, manX + 18, 165);
        g.setColor(new Color(145, 145, 138));
        g.drawLine(manX - 35, 95, 55, 95);
        g.drawLine(manX + 35, 95, width - 55, 95);
        drawCaption(g, "THE CELLAR");
    }

    private void drawCreature(Graphics2D g, int width, int height, boolean secondObjectVisible) {
        fillBackground(g, width, height, new Color(12, 16, 15));
        g.setColor(new Color(31, 45, 34));
        g.fillOval(width / 2 - 65, height - 130, 130, 105);
        g.setColor(new Color(44, 64, 42));
        g.fillRect(width / 2 - 24, 76, 48, height - 100);
        g.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(width / 2, 96, width / 2 - 55, 50);
        g.drawLine(width / 2, 96, width / 2 + 58, 42);
        g.drawLine(width / 2, 112, width / 2 - 73, 110);
        g.drawLine(width / 2, 112, width / 2 + 73, 105);
        g.setStroke(new BasicStroke(4));
        g.setColor(new Color(80, 95, 57));
        g.drawLine(width / 2 - 55, 50, width / 2 - 73, 35);
        g.drawLine(width / 2 + 58, 42, width / 2 + 75, 26);

        if (secondObjectVisible) {
            int objectX = width - 105;
            int objectY = height - 60;
            g.setColor(new Color(110, 200, 190, 35));
            g.fillOval(objectX - 28, objectY - 28, 56, 56);
            g.setColor(new Color(160, 255, 230));
            g.fillOval(objectX - 8, objectY - 8, 16, 16);
        }
        drawCaption(g, secondObjectVisible ? "THE SLEEPING BEAST" : "A CREATURE IN THE ROOM");
    }

    private void drawMineshaft(Graphics2D g, int width, int height) {
        fillBackground(g, width, height, new Color(25, 24, 23));
        g.setColor(new Color(87, 67, 48));
        g.fillPolygon(new int[]{width / 2 - 100, width / 2 + 100, width - 25, 25},
                new int[]{20, 20, height, height}, 4);
        g.setColor(new Color(20, 21, 21));
        g.fillOval(width / 2 - 72, 32, 144, height - 42);
        g.setColor(new Color(111, 87, 61));
        g.setStroke(new BasicStroke(7));
        g.drawArc(width / 2 - 80, 20, 160, 105, 180, 180);
        g.drawLine(width / 2 - 78, 70, width / 2 - 78, height - 14);
        g.drawLine(width / 2 + 78, 70, width / 2 + 78, height - 14);
        g.setColor(new Color(255, 200, 104, 100));
        g.fillOval(width / 2 - 20, height / 2 - 5, 40, 40);
        g.setColor(new Color(255, 220, 145));
        g.fillOval(width / 2 - 7, height / 2 + 8, 14, 14);
        drawCaption(g, "A LIGHT DEEP IN THE MINESHAFT");
    }

    private void fillBackground(Graphics2D g, int width, int height, Color color) {
        g.setColor(color);
        g.fillRect(0, 0, width, height);
    }

    private void drawCaption(Graphics2D g, String caption) {
        g.setColor(new Color(220, 220, 205));
        g.drawString(caption, 14, 18);
    }
}
