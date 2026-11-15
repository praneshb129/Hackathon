package org.hackathon.horror.story;

import java.awt.*;
import java.awt.geom.Point2D;

final class OutdoorScenePainter {
    private static final Font CAPTION_FONT = new Font("SansSerif", Font.BOLD, 12);

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
                drawCaption(g, "EMPTY ROAD");
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
        g.setColor(new Color(28, 33, 43));
        g.fillOval(-90, height - 122, 300, 112);
        g.fillOval(width - 215, height - 132, 300, 122);
        g.setColor(new Color(47, 49, 56));
        g.fillRect(0, height - 48, width, 48);
        g.setColor(new Color(82, 78, 72));
        g.drawLine(0, height - 49, width, height - 49);
        g.setColor(new Color(209, 195, 139));
        for (int x = 15; x < width; x += 85) {
            g.fillRect(x, height - 27, 42, 3);
        }
        g.setColor(new Color(255, 224, 153));
        g.fillOval(width - 68, height - 83, 30, 18);
        g.setColor(new Color(255, 232, 173, 45));
        g.fillOval(width - 132, height - 100, 90, 50);
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
        int centerX = width / 2;
        int vanishingY = Math.max(18, Math.min(height - 24, (int) (height * 0.34)));
        int nearY = height;
        int nearPathHalfWidth = (int) (width * 0.32);
        int farPathHalfWidth = Math.max(4, width / 90);

        g.setPaint(new GradientPaint(0, 0, new Color(8, 19, 17),
                0, height, new Color(17, 30, 23)));
        g.fillRect(0, 0, width, height);
        g.setPaint(new RadialGradientPaint(
                new Point2D.Float(centerX, vanishingY + 12),
                Math.max(width, height) * 0.62f,
                new float[]{0f, 0.38f, 1f},
                new Color[]{
                        new Color(173, 194, 164, 66),
                        new Color(99, 132, 112, 26),
                        new Color(0, 0, 0, 0)
                }));
        g.fillRect(0, 0, width, height);

        g.setPaint(new GradientPaint(0, vanishingY, new Color(31, 48, 37),
                0, height, new Color(9, 20, 16)));
        g.fillRect(0, vanishingY, width, height - vanishingY);

        drawTreeRow(g, width, height, centerX, vanishingY,
                farPathHalfWidth, nearPathHalfWidth, 0.12, 0.23,
                new Color(40, 61, 49));
        drawTreeRow(g, width, height, centerX, vanishingY,
                farPathHalfWidth, nearPathHalfWidth, 0.28, 0.34,
                new Color(31, 53, 42));

        Paint previousPaint = g.getPaint();
        g.setPaint(new GradientPaint(0, vanishingY - 8,
                new Color(174, 193, 177, 42),
                0, vanishingY + 50, new Color(147, 168, 153, 0)));
        g.fillRect(0, vanishingY - 8, width, 64);
        g.setPaint(previousPaint);

        int shadowHalfWidth = nearPathHalfWidth + Math.max(5, width / 90);
        fillPath(g, centerX, vanishingY, nearY,
                farPathHalfWidth + 3, shadowHalfWidth, new Color(5, 12, 11, 150));
        fillPath(g, centerX, vanishingY, nearY,
                farPathHalfWidth, nearPathHalfWidth,
                new GradientPaint(0, vanishingY, new Color(67, 77, 65),
                        0, height, new Color(30, 34, 30)));

        g.setStroke(new BasicStroke(Math.max(1f, width / 900f)));
        g.setColor(new Color(145, 159, 132, 105));
        g.drawLine(centerX - farPathHalfWidth, vanishingY,
                centerX - nearPathHalfWidth, nearY);
        g.drawLine(centerX + farPathHalfWidth, vanishingY,
                centerX + nearPathHalfWidth, nearY);
        drawPathStones(g, centerX, vanishingY, height,
                farPathHalfWidth, nearPathHalfWidth);

        drawTreeRow(g, width, height, centerX, vanishingY,
                farPathHalfWidth, nearPathHalfWidth, 0.48, 0.53,
                new Color(25, 47, 37));
        drawTreeRow(g, width, height, centerX, vanishingY,
                farPathHalfWidth, nearPathHalfWidth, 0.70, 0.73,
                new Color(20, 39, 31));
        drawTreeRow(g, width, height, centerX, vanishingY,
                farPathHalfWidth, nearPathHalfWidth, 0.88, 0.90,
                new Color(16, 33, 27));

        g.setColor(new Color(3, 10, 8, 105));
        g.fillOval(-width / 12, height - 25, width / 3, 36);
        g.fillOval(width - width / 3 + width / 12, height - 25, width / 3, 36);
        drawPerspectiveTree(g, width / 24, height + 18, 1.08,
                new Color(13, 29, 23));
        drawPerspectiveTree(g, width - width / 24, height + 18, 1.08,
                new Color(13, 29, 23));

        g.setColor(new Color(181, 193, 169, 24));
        g.fillOval(centerX - width / 3, vanishingY + 20, width * 2 / 3, 32);
        drawCaption(g, "THE WOODS");
    }

    private void drawTreeRow(Graphics2D g, int width, int height, int centerX,
                             int vanishingY, int farPathHalfWidth, int nearPathHalfWidth,
                             double depth, double scale, Color foliage) {
        int baseY = vanishingY + (int) Math.round((height - vanishingY) * depth);
        int pathHalfWidth = farPathHalfWidth
                + (int) Math.round((nearPathHalfWidth - farPathHalfWidth) * depth);
        int sideSpace = Math.max(0, width / 2 - pathHalfWidth);
        double[] positions = {0.34, 0.82};

        for (int side : new int[]{-1, 1}) {
            for (double position : positions) {
                int distance = pathHalfWidth + (int) Math.round(sideSpace * position);
                int treeX = centerX + side * distance;
                drawPerspectiveTree(g, treeX, baseY + 3, scale, foliage);
            }
        }
    }

    private void drawPerspectiveTree(Graphics2D g, int centerX, int baseY,
                                     double scale, Color foliage) {
        int treeHeight = Math.max(8, (int) Math.round(124 * scale));
        int crownWidth = Math.max(6, (int) Math.round(78 * scale));
        int trunkWidth = Math.max(2, (int) Math.round(12 * scale));
        int topY = baseY - treeHeight;
        int trunkTop = baseY - (int) (treeHeight * 0.54);

        g.setColor(new Color(4, 9, 8, 48));
        g.fillOval(centerX - crownWidth / 2, baseY - 4,
                Math.max(2, crownWidth), Math.max(2, (int) (8 * scale)));

        Polygon trunk = new Polygon(
                new int[]{centerX - trunkWidth / 3, centerX + trunkWidth / 3,
                        centerX + trunkWidth / 2, centerX - trunkWidth / 2},
                new int[]{trunkTop, trunkTop, baseY, baseY}, 4);
        g.setPaint(new GradientPaint(centerX - trunkWidth / 2f, trunkTop,
                new Color(83, 78, 60), centerX + trunkWidth / 2f, baseY,
                new Color(24, 30, 25)));
        g.fillPolygon(trunk);

        double[] topFractions = {0.00, 0.22, 0.45};
        double[] bottomFractions = {0.58, 0.80, 0.98};
        double[] widthFractions = {0.62, 0.84, 1.00};
        for (int tier = 0; tier < topFractions.length; tier++) {
            int tierTop = topY + (int) (treeHeight * topFractions[tier]);
            int tierBottom = topY + (int) (treeHeight * bottomFractions[tier]);
            int halfWidth = Math.max(3, (int) (crownWidth * widthFractions[tier] / 2));
            Polygon branches = new Polygon(
                    new int[]{centerX, centerX - halfWidth, centerX + halfWidth},
                    new int[]{tierTop, tierBottom, tierBottom}, 3);
            g.setColor(shade(foliage, tier == 0 ? 1.35 : tier == 1 ? 1.0 : 0.68));
            g.fillPolygon(branches);
            g.setColor(new Color(116, 148, 112, Math.max(18, (int) (62 * scale))));
            g.setStroke(new BasicStroke(Math.max(1f, (float) scale)));
            g.drawLine(centerX, tierTop + 3, centerX + halfWidth - 1, tierBottom - 2);
        }
    }

    private void fillPath(Graphics2D g, int centerX, int vanishingY, int nearY,
                          int farHalfWidth, int nearHalfWidth, Paint paint) {
        Paint previousPaint = g.getPaint();
        g.setPaint(paint);
        g.fillPolygon(
                new int[]{centerX - farHalfWidth, centerX + farHalfWidth,
                        centerX + nearHalfWidth, centerX - nearHalfWidth},
                new int[]{vanishingY, vanishingY, nearY, nearY}, 4);
        g.setPaint(previousPaint);
    }

    private void drawPathStones(Graphics2D g, int centerX, int vanishingY, int height,
                                int farHalfWidth, int nearHalfWidth) {
        double[] depths = {0.22, 0.38, 0.57, 0.74, 0.91};
        for (double depth : depths) {
            int y = vanishingY + (int) Math.round((height - vanishingY) * depth);
            int halfWidth = farHalfWidth
                    + (int) Math.round((nearHalfWidth - farHalfWidth) * depth);
            int stoneWidth = Math.max(2, (int) Math.round(3 + 12 * depth));
            int stoneHeight = Math.max(1, (int) Math.round(1 + 3 * depth));
            int offset = (int) Math.round(Math.sin(depth * 19) * halfWidth * 0.24);
            g.setColor(new Color(150, 157, 132, (int) (30 + 35 * depth)));
            g.fillOval(centerX + offset, y, stoneWidth, stoneHeight);
        }
    }

    private Color shade(Color color, double factor) {
        return new Color(
                Math.max(0, Math.min(255, (int) (color.getRed() * factor))),
                Math.max(0, Math.min(255, (int) (color.getGreen() * factor))),
                Math.max(0, Math.min(255, (int) (color.getBlue() * factor))));
    }

    private void fillBackground(Graphics2D g, int width, int height, Color color) {
        g.setPaint(new GradientPaint(0, 0, color, 0, Math.max(1, height), color.darker()));
        g.fillRect(0, 0, width, height);
        g.setColor(color);
    }

    private void drawCaption(Graphics2D g, String caption) {
        Font previousFont = g.getFont();
        g.setFont(CAPTION_FONT);
        FontMetrics metrics = g.getFontMetrics();
        int x = 10;
        int y = 6;
        g.setColor(new Color(7, 10, 15, 185));
        g.fillRoundRect(x, y, metrics.stringWidth(caption) + 16, 24, 8, 8);
        g.setColor(new Color(229, 227, 216));
        g.drawString(caption, x + 8, y + 16);
        g.setFont(previousFont);
    }
}
