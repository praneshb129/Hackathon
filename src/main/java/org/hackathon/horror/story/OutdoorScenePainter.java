package org.hackathon.horror.story;

import java.awt.*;

final class OutdoorScenePainter {
    void paint(Graphics2D g, int width, int height, Scene scene) {
        switch (scene) {
            case START:
                drawTrail(g, width, height);
                break;
            case GATES:
                drawGates(g, width, height);
                break;
            case HATCHET_FIGHT:
                drawHatchetEncounter(g, width, height);
                break;
            case FOREST_DASH:
            case FOREST_LEAVE_NOW:
            case FOREST_WAIT:
            case FOREST_LEAVE_QUIETLY:
            case FOREST_BREAK_RUN:
                drawWoods(g, width, height);
                break;
            case CROSS_STREET:
            case WALK:
                drawCreatureOnRoad(g, width, height);
                break;
            case KEEP_SPRINTING:
            case LEFT_ROAD:
            case RUN:
            case KEEP_RUNNING:
            case WOODS_PATH_CHOICE:
                drawRoad(g, width, height);
                break;
            default:
                throw new IllegalArgumentException("Not an outdoor scene: " + scene);
        }
    }

    private void drawTrail(Graphics2D g, int width, int height) {
        fillBackground(g, width, height, new Color(19, 35, 33));
        g.setColor(new Color(54, 74, 57));
        g.fillOval(-60, -35, 190, 160);
        g.fillOval(20, 15, 150, 140);
        g.fillOval(width - 150, -30, 200, 180);
        g.setColor(new Color(110, 93, 68));
        Polygon path = new Polygon(
                new int[]{width / 2 - 25, width / 2 + 25, width + 40, -40},
                new int[]{height / 2, height / 2, height, height},
                4);
        g.fillPolygon(path);
        g.setColor(new Color(142, 131, 105));
        g.drawLine(width / 2, height / 2 + 10, width / 2, height);
        drawCaption(g, "THE TRAIL");
    }

    private void drawGates(Graphics2D g, int width, int height) {
        fillBackground(g, width, height, new Color(30, 42, 48));
        int gateWidth = Math.min(250, width - 70);
        int left = (width - gateWidth) / 2;
        int top = 25;
        int bottom = height - 25;
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
            g.fillPolygon(new int[]{x - 6, x, x + 6},
                    new int[]{top + 8, top - 2, top + 8}, 3);
        }
        g.drawLine(left, top + 45, left + gateWidth, top + 45);
        g.drawLine(left, bottom - 35, left + gateWidth, bottom - 35);
        drawCaption(g, "IRON GATES");
    }

    private void drawRoad(Graphics2D g, int width, int height) {
        fillBackground(g, width, height, new Color(19, 24, 37));
        g.setColor(new Color(47, 49, 56));
        g.fillRect(0, height - 48, width, 48);
        g.setColor(new Color(209, 195, 139));
        for (int x = 15; x < width; x += 85) {
            g.fillRect(x, height - 27, 42, 3);
        }
        g.setColor(new Color(255, 224, 153));
        g.fillOval(width - 68, height - 83, 30, 18);
        g.setColor(new Color(255, 232, 173, 45));
        g.fillOval(width - 132, height - 100, 90, 50);
        drawCaption(g, "EMPTY ROAD");
    }

    private void drawHatchetEncounter(Graphics2D g, int width, int height) {
        drawRoad(g, width, height);
        g.setColor(new Color(52, 61, 43));
        g.fillOval(width - 150, height - 135, 95, 78);
        g.setColor(new Color(125, 142, 116));
        g.fillOval(width - 131, height - 116, 12, 9);
        g.fillOval(width - 94, height - 116, 12, 9);
        g.setColor(new Color(170, 170, 155));
        g.setStroke(new BasicStroke(5));
        g.drawLine(85, height - 42, 117, height - 90);
        g.setColor(new Color(115, 91, 64));
        g.drawLine(78, height - 82, 111, height - 97);
        g.drawLine(111, height - 97, 124, height - 82);
        drawCaption(g, "A SNARLING SHAPE AND A BROKEN HATCHET");
    }

    private void drawCreatureOnRoad(Graphics2D g, int width, int height) {
        drawRoad(g, width, height);
        g.setColor(new Color(38, 57, 39));
        g.fillOval(width - 145, height - 132, 82, 72);
        g.setColor(new Color(255, 75, 70));
        g.fillOval(width - 126, height - 108, 12, 8);
        g.fillOval(width - 91, height - 108, 12, 8);
        g.setColor(new Color(59, 75, 47));
        g.fillRect(width - 112, height - 76, 20, 45);
        drawCaption(g, "THE CREATURE CHARGES");
    }

    private void drawWoods(Graphics2D g, int width, int height) {
        fillBackground(g, width, height, new Color(12, 24, 20));
        g.setColor(new Color(31, 48, 36));
        for (int x = -20; x < width + 40; x += 75) {
            g.fillPolygon(
                    new int[]{x, x + 36, x + 72},
                    new int[]{height, 18 + (x % 3) * 8, height},
                    3);
            g.setColor(new Color(70, 54, 39));
            g.fillRect(x + 31, 75, 10, height - 75);
            g.setColor(new Color(31, 48, 36));
        }
        g.setColor(new Color(55, 53, 44));
        g.fillPolygon(
                new int[]{width / 2 - 15, width / 2 + 15, width + 30, -30},
                new int[]{height / 2, height / 2, height, height},
                4);
        drawCaption(g, "THE WOODS");
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
