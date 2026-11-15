package org.hackathon.horror.story;

import java.awt.*;
import java.awt.geom.Point2D;

final class OutdoorScenePainter {
    private static final Font CAPTION_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11)
            .deriveFont(java.util.Map.of(java.awt.font.TextAttribute.TRACKING, 0.12f));

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
                drawWoods(g, width, height, scene);
                break;
            case CROSS_STREET:
            case WALK:
                drawRoad(g, width, height, false, false);
                drawCreatureOnRoad(g, width, height);
                break;
            case KEEP_SPRINTING:
            case LEFT_ROAD:
            case RUN:
            case KEEP_RUNNING:
            case WOODS_PATH_CHOICE:
                drawRoad(g, width, height, scene == Scene.LEFT_ROAD, true);
                break;
            default:
                throw new IllegalArgumentException("Not an outdoor scene: " + scene);
        }
    }

    private void drawTrail(Graphics2D g, int width, int height) {
        int horizon = (int) (height * 0.57);
        drawNightSky(g, width, height, new Color(8, 19, 17), new Color(31, 58, 51));
        drawTreeLine(g, width, height, horizon, 0.38, 0.50, new Color(24, 43, 34));
        g.setPaint(new GradientPaint(0, horizon, new Color(28, 46, 35),
                0, height, new Color(12, 21, 17)));
        g.fillRect(0, horizon, width, height - horizon);

        int centerX = width / 2;
        int forkY = (int) (height * 0.69);
        g.setColor(new Color(66, 58, 43));
        g.fillPolygon(new int[]{centerX - 5, centerX + 5, width + 30, -30},
                new int[]{forkY, forkY, height, height}, 4);
        g.setColor(new Color(78, 66, 47));
        g.fillPolygon(new int[]{centerX - 5, centerX + 5, centerX + width / 2, centerX + 20},
                new int[]{forkY, forkY, forkY - 5, forkY - 17}, 4);
        g.fillPolygon(new int[]{centerX - 5, centerX + 5, centerX - 20, centerX - width / 2},
                new int[]{forkY, forkY, forkY - 17, forkY - 5}, 4);
        g.setColor(new Color(171, 146, 99, 95));
        g.setStroke(new BasicStroke(Math.max(1f, width / 1000f)));
        g.drawLine(centerX, forkY + 10, centerX, height);

        drawPine(g, width, height, width / 8, height * 0.74, 0.58, new Color(28, 48, 37));
        drawPine(g, width, height, width - width / 7, height * 0.74, 0.62, new Color(25, 45, 35));
        drawSignpost(g, Math.max(26, centerX - width / 8), forkY);
        drawNightGlow(g, width * 0.74, height * 0.15, Math.max(8, height * 0.08),
                new Color(221, 193, 139, 180));
        drawVignette(g, width, height, 0.58f);
        drawCaption(g, "THE TRAIL");
    }

    private void drawGates(Graphics2D g, int width, int height) {
        int horizon = (int) (height * 0.67);
        drawNightSky(g, width, height, new Color(6, 12, 18), new Color(30, 42, 48));
        drawTreeLine(g, width, height, horizon, 0.35, 0.63, new Color(20, 34, 29));
        g.setColor(new Color(18, 24, 22));
        g.fillRect(0, horizon, width, height - horizon);
        int manorWidth = Math.min(width * 3 / 5, 550);
        int manorLeft = (width - manorWidth) / 2;
        int manorTop = Math.max(25, horizon - height / 3);
        g.setColor(new Color(15, 18, 22));
        g.fillRect(manorLeft, manorTop + height / 8, manorWidth, horizon - manorTop);
        g.fillPolygon(new int[]{manorLeft - 20, width / 2, manorLeft + manorWidth + 20},
                new int[]{manorTop + height / 8, manorTop, manorTop + height / 8}, 3);
        g.setColor(new Color(217, 164, 65, 150));
        g.fillRect(width / 2 - 5, manorTop + height / 5, 10, 14);

        int gateWidth = Math.min(300, width - 70);
        int left = (width - gateWidth) / 2;
        int top = Math.max(20, horizon - height * 3 / 5);
        int bottom = height - 16;
        g.setPaint(new GradientPaint(left, top, new Color(110, 103, 87),
                left + gateWidth, bottom, new Color(43, 43, 43)));
        g.fillRect(left - 15, top - 8, 15, bottom - top + 8);
        g.fillRect(left + gateWidth, top - 8, 15, bottom - top + 8);
        g.setColor(new Color(12, 15, 18, 225));
        g.fillRect(left, top, gateWidth, bottom - top);
        g.setColor(new Color(112, 116, 113));
        g.setStroke(new BasicStroke(Math.max(2f, width / 400f)));
        g.drawRect(left, top, gateWidth, bottom - top);
        for (int x = left + 15; x < left + gateWidth; x += Math.max(15, gateWidth / 16)) {
            int point = Math.max(5, width / 110);
            g.drawLine(x, top + point, x, bottom);
            g.fillPolygon(new int[]{x - point, x, x + point},
                    new int[]{top + point, top - point, top + point}, 3);
        }
        g.drawLine(left, top + height / 7, left + gateWidth, top + height / 7);
        g.drawLine(left, bottom - height / 8, left + gateWidth, bottom - height / 8);
        g.setColor(new Color(103, 99, 86));
        g.fillRect(left - 21, bottom - 12, gateWidth + 42, 12);
        drawVignette(g, width, height, 0.45f);
        drawCaption(g, "IRON GATES");
    }

    private void drawRoad(Graphics2D g, int width, int height, boolean dusk, boolean headlights) {
        Color top = dusk ? new Color(28, 21, 48) : new Color(4, 6, 12);
        Color horizon = dusk ? new Color(160, 83, 60) : new Color(20, 27, 38);
        int roadTop = (int) (height * 0.64);
        drawNightSky(g, width, height, top, horizon);
        drawTreeLine(g, width, height, roadTop, 0.34, 0.53,
                dusk ? new Color(45, 34, 39) : new Color(18, 29, 25));
        g.setPaint(new GradientPaint(0, roadTop - 8, new Color(22, 27, 27),
                0, height, new Color(10, 13, 14)));
        g.fillRect(0, roadTop, width, height - roadTop);

        int roadHeight = Math.max(30, height - roadTop);
        g.setPaint(new GradientPaint(0, roadTop, new Color(55, 55, 54),
                0, height, new Color(36, 38, 40)));
        g.fillRect(0, roadTop, width, roadHeight);
        g.setColor(new Color(116, 108, 91, 125));
        g.setStroke(new BasicStroke(Math.max(1f, width / 1000f)));
        g.drawLine(0, roadTop, width, roadTop);

        int markerY = roadTop + roadHeight * 2 / 3;
        int markerWidth = Math.max(14, width / 30);
        int markerHeight = Math.max(2, height / 100);
        g.setColor(new Color(209, 195, 139, 205));
        for (int x = width / 25; x < width; x += Math.max(36, width / 9)) {
            g.fillRoundRect(x, markerY, markerWidth, markerHeight, markerHeight, markerHeight);
        }
        if (dusk) {
            drawNightGlow(g, width * 0.82, roadTop - height * 0.12,
                    Math.max(18, height * 0.20), new Color(255, 154, 90, 150));
            g.setColor(new Color(255, 207, 138));
            g.fillOval((int) (width * 0.80), roadTop - height / 8, Math.max(12, width / 45),
                    Math.max(12, width / 45));
        }
        if (headlights) {
            int lampX = width - Math.max(50, width / 9);
            int lampY = roadTop + roadHeight / 3;
            g.setPaint(new GradientPaint(lampX, lampY,
                    new Color(255, 224, 160, 95), lampX - width / 2f, lampY,
                    new Color(255, 224, 160, 0)));
            g.fillPolygon(new int[]{lampX, lampX - width / 2, lampX - width / 2, lampX},
                    new int[]{lampY - height / 5, lampY - height / 3, lampY + height / 3, lampY + height / 5}, 4);
            drawNightGlow(g, lampX, lampY, Math.max(18, width / 18),
                    new Color(255, 232, 173, 100));
            g.setColor(new Color(255, 224, 153));
            g.fillOval(lampX - 8, lampY - 5, 16, 10);
        }
        drawVignette(g, width, height, 0.50f);
        drawCaption(g, "EMPTY ROAD");
    }

    private void drawHatchetEncounter(Graphics2D g, int width, int height) {
        drawRoad(g, width, height, false, false);
        drawCreatureSilhouette(g, width - width / 7, (int) (height * 0.75),
                Math.max(0.55, height / 180.0), new Color(36, 45, 33), new Color(159, 184, 143));
        drawNightGlow(g, width - width / 7, height * 0.57, height * 0.2,
                new Color(159, 184, 143, 28));
        int hatchetX = Math.max(48, width / 8);
        int hatchetY = height - Math.max(28, height / 6);
        g.setColor(new Color(115, 91, 64));
        g.setStroke(new BasicStroke(Math.max(3f, width / 220f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(hatchetX, hatchetY, hatchetX + width / 28, hatchetY - height / 4);
        g.setColor(new Color(170, 170, 155));
        g.setStroke(new BasicStroke(Math.max(4f, width / 150f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(hatchetX + width / 45, hatchetY - height / 5,
                hatchetX + width / 12, hatchetY - height / 4);
        drawCaption(g, "A SNARLING SHAPE AND A BROKEN HATCHET");
    }

    private void drawCreatureOnRoad(Graphics2D g, int width, int height) {
        drawCreatureSilhouette(g, width - width / 7, (int) (height * 0.79),
                Math.max(0.7, height / 150.0), new Color(33, 52, 36), new Color(255, 75, 70));
        drawNightGlow(g, width - width / 7, height * 0.57, height * 0.25,
                new Color(255, 75, 70, 32));
        drawCaption(g, "THE CREATURE CHARGES");
    }

    private void drawWoods(Graphics2D g, int width, int height, Scene scene) {
        int centerX = width / 2;
        int vanishingY = Math.max(18, Math.min(height - 24, (int) (height * 0.34)));
        int nearY = height;
        int nearPathHalfWidth = (int) (width * 0.32);
        int farPathHalfWidth = Math.max(4, width / 90);

        g.setPaint(new GradientPaint(0, 0, new Color(4, 9, 7),
                0, height, new Color(18, 34, 26)));
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
        drawPerspectiveTree(g, width, height, width / 24, height + 18, 1.08,
                new Color(13, 29, 23));
        drawPerspectiveTree(g, width, height, width - width / 24, height + 18, 1.08,
                new Color(13, 29, 23));

        for (int i = 0; i < 4; i++) {
            int eyeX = centerX + (i % 2 == 0 ? -1 : 1)
                    * (nearPathHalfWidth + Math.max(12, width / 20) + (i / 2) * Math.max(12, width / 24));
            int eyeY = vanishingY + (int) ((height - vanishingY) * (0.36 + (i / 2) * 0.18));
            int eyeSize = Math.max(2, width / 300);
            g.setColor(new Color(255, 48, 32, 90));
            g.fillOval(eyeX - eyeSize * 2, eyeY - eyeSize * 2, eyeSize * 4, eyeSize * 4);
            g.setColor(new Color(255, 76, 48, 220));
            g.fillOval(eyeX - eyeSize - eyeSize / 2, eyeY, eyeSize, eyeSize);
            g.fillOval(eyeX + eyeSize / 2, eyeY, eyeSize, eyeSize);
        }
        if (scene == Scene.FOREST_LEAVE_NOW) {
            int beastX = centerX;
            int beastY = vanishingY + Math.max(12, height / 14);
            drawCreatureSilhouette(g, beastX, beastY + height / 9,
                    Math.max(0.42, height / 280.0), new Color(20, 30, 24), new Color(255, 74, 58));
        }
        g.setColor(new Color(181, 193, 169, 24));
        g.fillOval(centerX - width / 3, vanishingY + 20, width * 2 / 3, 32);
        drawVignette(g, width, height, 0.54f);
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
                drawPerspectiveTree(g, width, height, treeX, baseY + 3, scale, foliage);
            }
        }
    }

    private void drawPerspectiveTree(Graphics2D g, int width, int height, int centerX, int baseY,
                                     double scale, Color foliage) {
        double displayScale = Math.max(0.55, height / 190.0);
        int treeHeight = Math.max(8, (int) Math.round(124 * scale * displayScale));
        int crownWidth = Math.max(6, (int) Math.round(78 * scale * width / 500.0));
        int trunkWidth = Math.max(2, (int) Math.round(12 * scale * width / 500.0));
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

    private void drawNightSky(Graphics2D g, int width, int height, Color top, Color horizon) {
        g.setPaint(new GradientPaint(0, 0, top, 0, height, horizon));
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < 36; i++) {
            int x = Math.floorMod(i * 83 + 17, Math.max(1, width));
            int y = Math.floorMod(i * 47 + 11, Math.max(1, height / 2));
            int diameter = i % 7 == 0 ? 2 : 1;
            g.setColor(new Color(198, 208, 191, 55 + i % 4 * 18));
            g.fillOval(x, y, diameter, diameter);
        }
        drawNightGlow(g, width * 0.79, height * 0.16, Math.max(8, height * 0.07),
                new Color(178, 191, 198, 70));
        g.setColor(new Color(222, 222, 207, 165));
        int moonSize = Math.max(5, height / 28);
        g.fillOval((int) (width * 0.79) - moonSize / 2, (int) (height * 0.16) - moonSize / 2,
                moonSize, moonSize);
    }

    private void drawTreeLine(Graphics2D g, int width, int height, int horizon, double nearScale,
                              double farScale, Color foliage) {
        int count = Math.max(8, width / 65);
        for (int i = 0; i <= count; i++) {
            double fraction = (double) i / count;
            int x = (int) Math.round(fraction * width);
            double scale = farScale + (nearScale - farScale) * (i % 3) / 2.0;
            int base = horizon + Math.max(2, (int) (horizon * (i % 2 == 0 ? 0.04 : 0.09)));
            drawPerspectiveTree(g, width, height, x, base, scale, foliage);
        }
    }

    private void drawPine(Graphics2D g, int width, int height, int centerX, double baseY,
                          double scale, Color foliage) {
        drawPerspectiveTree(g, width, height, centerX, (int) Math.round(baseY), scale, foliage);
    }

    private void drawSignpost(Graphics2D g, int x, int baseY) {
        int postWidth = Math.max(3, getScaled(6, baseY));
        int postHeight = Math.max(25, baseY / 2);
        g.setColor(new Color(58, 42, 28));
        g.fillRect(x, baseY - postHeight, postWidth, postHeight);
        g.setColor(new Color(103, 72, 43));
        g.fillRect(x - postWidth * 2, baseY - postHeight, postWidth * 7, Math.max(5, postWidth));
        g.setColor(new Color(30, 25, 21));
        g.drawRect(x - postWidth * 2, baseY - postHeight, postWidth * 7, Math.max(5, postWidth));
    }

    private int getScaled(int amount, int height) {
        return (int) Math.round(amount * Math.max(0.6, height / 190.0));
    }

    private void drawNightGlow(Graphics2D g, double centerX, double centerY, double radius, Color color) {
        float safeRadius = Math.max(1f, (float) radius);
        Color clear = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
        Paint previousPaint = g.getPaint();
        g.setPaint(new RadialGradientPaint(
                new Point2D.Float((float) centerX, (float) centerY), safeRadius,
                new float[]{0f, 1f}, new Color[]{color, clear}));
        g.fillOval((int) Math.round(centerX - safeRadius), (int) Math.round(centerY - safeRadius),
                (int) Math.ceil(safeRadius * 2), (int) Math.ceil(safeRadius * 2));
        g.setPaint(previousPaint);
    }

    private void drawVignette(Graphics2D g, int width, int height, float edgeAlpha) {
        Paint previousPaint = g.getPaint();
        float radius = Math.max(1f, (float) (Math.max(width, height) * 0.76));
        g.setPaint(new RadialGradientPaint(
                new Point2D.Float(width / 2f, height / 2f), radius,
                new float[]{0f, 0.62f, 1f},
                new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 18),
                        new Color(0, 0, 0, Math.max(0, Math.min(255, (int) (edgeAlpha * 255))))}));
        g.fillRect(0, 0, width, height);
        g.setPaint(previousPaint);
    }

    private void drawCreatureSilhouette(Graphics2D g, int centerX, int baseY, double scale,
                                        Color bodyColor, Color eyes) {
        int headSize = Math.max(10, (int) Math.round(34 * scale));
        int bodyWidth = Math.max(12, (int) Math.round(40 * scale));
        int bodyHeight = Math.max(16, (int) Math.round(62 * scale));
        int headY = baseY - bodyHeight - headSize + 4;
        g.setColor(new Color(0, 0, 0, 85));
        g.fillOval(centerX - bodyWidth, baseY - 5, bodyWidth * 2, Math.max(5, (int) (12 * scale)));
        g.setColor(bodyColor);
        g.fillPolygon(new int[]{centerX - bodyWidth / 2, centerX + bodyWidth / 2,
                        centerX + bodyWidth / 3, centerX - bodyWidth / 3},
                new int[]{baseY - bodyHeight, baseY - bodyHeight, baseY, baseY}, 4);
        g.fillOval(centerX - headSize / 2, headY, headSize, headSize);
        g.setStroke(new BasicStroke(Math.max(2f, (float) (8 * scale)),
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(centerX - bodyWidth / 3, baseY - bodyHeight + 8,
                centerX - bodyWidth, baseY - bodyHeight - headSize / 2);
        g.drawLine(centerX + bodyWidth / 3, baseY - bodyHeight + 8,
                centerX + bodyWidth, baseY - bodyHeight - headSize / 2);
        g.drawLine(centerX - bodyWidth, baseY - bodyHeight - headSize / 2,
                centerX - bodyWidth - headSize / 3, headY - headSize / 3);
        g.drawLine(centerX + bodyWidth, baseY - bodyHeight - headSize / 2,
                centerX + bodyWidth + headSize / 3, headY - headSize / 3);
        g.setColor(eyes);
        int eyeSize = Math.max(2, headSize / 8);
        g.fillOval(centerX - headSize / 4 - eyeSize / 2, headY + headSize / 2, eyeSize, eyeSize);
        g.fillOval(centerX + headSize / 4 - eyeSize / 2, headY + headSize / 2, eyeSize, eyeSize);
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
        int x = 14;
        int y = 24;
        g.setColor(new Color(0, 0, 0, 180));
        g.drawString(caption, x + 1, y + 1);
        g.setColor(new Color(220, 220, 205, 220));
        g.drawString(caption, x, y);
        g.setFont(previousFont);
    }
}
