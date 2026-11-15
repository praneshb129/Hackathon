package org.hackathon.horror.story;

import java.awt.*;
import java.awt.geom.Point2D;

final class IndoorScenePainter {
    private static final Font CAPTION_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11)
            .deriveFont(java.util.Map.of(java.awt.font.TextAttribute.TRACKING, 0.12f));

    void paint(Graphics2D g, int width, int height, Scene scene, int personX) {
        switch (scene) {
            case MANOR_DOOR:
                drawManor(g, width, height);
                break;
            case DOOR_KICKED:
            case ENTER_MANOR:
                drawChamber(g, width, height, scene);
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
            case CELLAR_CAUGHT:
                drawCellar(g, width, height);
                break;
            case VENT_DISCOVERY:
                drawDimRoom(g, width, height, true);
                break;
            case VENT_CREATURE_CHOICE:
                drawCreature(g, width, height, false, scene);
                break;
            case CREATURE_ROOM:
            case GRAB_SECOND_OBJECT:
                drawCreature(g, width, height, true, scene);
                break;
            case MINESHAFT_ENTER:
            case MINESHAFT_IGNORE:
                drawMineshaft(g, width, height, scene);
                break;
            default:
                throw new IllegalArgumentException("Not an indoor scene: " + scene);
        }
    }

    private void drawManor(Graphics2D g, int width, int height) {
        drawNightBackdrop(g, width, height, new Color(5, 8, 14), new Color(18, 22, 34));
        int groundY = (int) (height * 0.88);
        g.setPaint(new GradientPaint(0, groundY, new Color(36, 39, 34),
                0, height, new Color(13, 16, 15)));
        g.fillRect(0, groundY, width, height - groundY);

        int buildingLeft = width / 5;
        int buildingRight = width - buildingLeft;
        int roofY = (int) (height * 0.17);
        int wallTop = (int) (height * 0.36);
        g.setColor(new Color(20, 21, 27));
        g.fillRect(buildingLeft, wallTop, buildingRight - buildingLeft, groundY - wallTop);
        g.fillPolygon(new int[]{buildingLeft - width / 18, width / 2, buildingRight + width / 18},
                new int[]{wallTop, roofY, wallTop}, 3);
        int towerWidth = Math.max(24, width / 10);
        for (int side : new int[]{-1, 1}) {
            int towerX = width / 2 + side * (buildingRight - width / 2 - towerWidth / 2);
            g.setColor(new Color(25, 26, 31));
            g.fillRect(towerX - towerWidth / 2, (int) (height * 0.20),
                    towerWidth, groundY - (int) (height * 0.20));
            g.fillPolygon(new int[]{towerX - towerWidth / 2 - 4, towerX, towerX + towerWidth / 2 + 4},
                    new int[]{(int) (height * 0.22), (int) (height * 0.08), (int) (height * 0.22)}, 3);
        }

        g.setColor(new Color(38, 35, 34));
        for (int y = wallTop + height / 10; y < groundY; y += Math.max(10, height / 8)) {
            g.drawLine(buildingLeft, y, buildingRight, y);
        }
        int windowSize = Math.max(9, width / 32);
        g.setColor(new Color(217, 164, 65, 185));
        for (int side : new int[]{-1, 1}) {
            int windowX = width / 2 + side * width / 6 - windowSize / 2;
            int windowY = wallTop + height / 12;
            g.fillRect(windowX, windowY, windowSize, windowSize);
            g.fillRect(windowX, windowY + height / 6, windowSize, windowSize);
            g.setColor(new Color(24, 25, 30));
            g.drawLine(windowX + windowSize / 2, windowY, windowX + windowSize / 2,
                    windowY + windowSize);
            g.setColor(new Color(217, 164, 65, 185));
        }

        int doorWidth = Math.max(30, width / 13);
        int doorX = width / 2 - doorWidth / 2;
        int doorY = groundY - (int) (height * 0.40);
        int doorHeight = groundY - doorY;
        g.setColor(new Color(63, 43, 31));
        g.fillRect(doorX, doorY, doorWidth, doorHeight);
        g.setColor(new Color(27, 24, 22));
        g.setStroke(new BasicStroke(Math.max(2f, width / 500f)));
        g.drawRect(doorX, doorY, doorWidth, doorHeight);
        g.drawLine(width / 2, doorY + 3, width / 2, groundY - 2);
        g.setColor(new Color(217, 164, 65));
        int knobSize = Math.max(3, width / 140);
        g.fillOval(doorX + doorWidth * 3 / 4, doorY + doorHeight / 2,
                knobSize, knobSize);
        g.setColor(new Color(108, 105, 97));
        g.fillPolygon(new int[]{doorX - width / 12, doorX + doorWidth + width / 12,
                        doorX + doorWidth + width / 8, doorX - width / 8},
                new int[]{groundY, groundY, groundY + height / 18, groundY + height / 18}, 4);
        drawVignette(g, width, height);
        drawCaption(g, "THE OLD MANOR");
    }

    private void drawChamber(Graphics2D g, int width, int height, Scene scene) {
        fillBackground(g, width, height, new Color(12, 11, 18));
        int wallLeft = width / 16;
        int wallRight = width - wallLeft;
        int wallTop = height / 8;
        int floorY = (int) (height * 0.76);
        g.setPaint(new GradientPaint(0, wallTop, new Color(68, 64, 74),
                0, floorY, new Color(35, 33, 38)));
        g.fillRect(wallLeft, wallTop, wallRight - wallLeft, floorY - wallTop);
        g.setColor(new Color(99, 91, 87, 100));
        for (int y = wallTop + height / 8; y < floorY; y += Math.max(8, height / 8)) {
            g.drawLine(wallLeft, y, wallRight, y);
            int offset = ((y / Math.max(1, height / 8)) % 2) * (width / 12);
            for (int x = wallLeft + offset; x < wallRight; x += Math.max(20, width / 8)) {
                g.drawLine(x, y - height / 8, x, y);
            }
        }
        g.setPaint(new GradientPaint(0, floorY, new Color(48, 43, 36),
                0, height, new Color(22, 20, 19)));
        g.fillPolygon(new int[]{wallLeft, wallRight, width, 0},
                new int[]{floorY, floorY, height, height}, 4);
        int pedestalX = width / 2;
        int pedestalY = (int) (height * 0.68);
        g.setColor(new Color(64, 58, 52));
        g.fillPolygon(new int[]{pedestalX - width / 12, pedestalX + width / 12,
                        pedestalX + width / 10, pedestalX - width / 10},
                new int[]{pedestalY, pedestalY, floorY, floorY}, 4);
        g.setColor(new Color(100, 88, 70));
        g.fillRect(pedestalX - width / 12, pedestalY, width / 6, Math.max(4, height / 30));
        int orbX = pedestalX;
        int orbY = height / 2;
        drawGlow(g, orbX, orbY, Math.max(25, height / 3), new Color(255, 215, 80, 80));
        g.setColor(new Color(255, 218, 112, 110));
        g.fillOval(orbX - height / 10, orbY - height / 10, height / 5, height / 5);
        g.setColor(new Color(255, 244, 190));
        g.fillOval(orbX - height / 24, orbY - height / 24, height / 12, height / 12);
        for (int i = 0; i < 26; i++) {
            int x = Math.floorMod(i * 83 + 17, Math.max(1, width));
            int y = Math.floorMod(i * 47 + 11, Math.max(1, height));
            g.setColor(new Color(255, 230, 160, 85));
            g.fillOval(x, y, Math.max(1, width / 500), Math.max(1, width / 500));
        }
        if (scene == Scene.DOOR_KICKED) {
            drawDoorShards(g, width, height);
        }
        drawVignette(g, width, height);
        drawCaption(g, "A GLOWING OBJECT");
    }

    private void drawDarkRoom(Graphics2D g, int width, int height,
                              boolean hasLamp, boolean hasShards, int personX) {
        g.setPaint(new GradientPaint(0, 0, new Color(0, 0, 0),
                0, height, new Color(6, 6, 7)));
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(45, 43, 39, 34));
        g.drawLine(0, height - 16, width, height - 16);
        if (hasLamp) {
            int lampX = personX + Math.max(10, width / 60);
            int lampY = height - height / 3;
            drawGlow(g, lampX, lampY, Math.max(45, height * 0.72), new Color(255, 215, 80, 32));
            drawGlow(g, lampX, lampY, Math.max(20, height * 0.36), new Color(255, 215, 80, 55));
            g.setColor(new Color(255, 244, 190, 100));
            int lampSize = Math.max(8, height / 14);
            g.fillOval(lampX - lampSize / 2, lampY - lampSize / 2, lampSize, lampSize);
        }
        if (hasShards) {
            g.setColor(new Color(180, 190, 200));
            int baseY = height - height / 7;
            for (int i = -1; i <= 1; i++) {
                int x = width / 2 + i * width / 16;
                int shardHeight = height / (3 + Math.abs(i));
                g.fillPolygon(new int[]{x - width / 60, x, x + width / 55},
                        new int[]{baseY, baseY - shardHeight, baseY - height / 45}, 3);
                g.setColor(new Color(233, 239, 242, 170));
                g.drawLine(x, baseY - shardHeight, x + width / 70, baseY - height / 45);
                g.setColor(new Color(180, 190, 200));
            }
        }
        drawVignette(g, width, height);
    }

    private void drawDimRoom(Graphics2D g, int width, int height, boolean ventVisible) {
        drawDimRoom(g, width, height, ventVisible, true);
    }

    private void drawDimRoom(Graphics2D g, int width, int height,
                             boolean ventVisible, boolean showCaption) {
        fillBackground(g, width, height, new Color(15, 13, 11));
        int wallLeft = width / 14;
        int wallRight = width - wallLeft;
        int wallTop = height / 8;
        int floorY = (int) (height * 0.74);
        g.setPaint(new GradientPaint(0, wallTop, new Color(83, 75, 65),
                0, floorY, new Color(47, 42, 37)));
        g.fillRect(wallLeft, wallTop, wallRight - wallLeft, floorY - wallTop);
        g.setColor(new Color(110, 97, 82, 70));
        for (int y = wallTop + height / 8; y < floorY; y += Math.max(10, height / 7)) {
            g.drawLine(wallLeft, y, wallRight, y);
            int offset = ((y / Math.max(1, height / 7)) % 2) * (width / 12);
            for (int x = wallLeft + offset; x < wallRight; x += Math.max(25, width / 7)) {
                g.drawLine(x, y - height / 7, x, y);
            }
        }
        g.setPaint(new GradientPaint(0, floorY, new Color(63, 53, 43),
                0, height, new Color(25, 22, 19)));
        g.fillPolygon(new int[]{wallLeft, wallRight, width, 0},
                new int[]{floorY, floorY, height, height}, 4);

        int windowX = width / 5;
        int windowY = height / 5;
        int windowWidth = Math.max(28, width / 7);
        int windowHeight = Math.max(22, height / 4);
        g.setColor(new Color(39, 38, 36));
        g.fillRect(windowX - 6, windowY - 5, windowWidth + 12, windowHeight + 10);
        g.setPaint(new GradientPaint(windowX, windowY, new Color(154, 171, 183),
                windowX, windowY + windowHeight, new Color(35, 44, 49)));
        g.fillRect(windowX, windowY, windowWidth, windowHeight);
        g.setColor(new Color(112, 103, 90));
        g.setStroke(new BasicStroke(Math.max(2f, width / 300f)));
        g.drawLine(windowX, windowY, windowX + windowWidth, windowY + windowHeight);
        g.drawLine(windowX + windowWidth, windowY, windowX, windowY + windowHeight);
        g.drawLine(windowX + windowWidth / 2, windowY, windowX + windowWidth / 2, windowY + windowHeight);
        g.drawLine(windowX, windowY + windowHeight / 2, windowX + windowWidth, windowY + windowHeight / 2);

        g.setPaint(new GradientPaint(windowX + windowWidth / 2f, windowY + windowHeight,
                new Color(184, 197, 208, 38), width / 2f, floorY,
                new Color(184, 197, 208, 0)));
        g.fillPolygon(new int[]{windowX, windowX + windowWidth, width / 2 + width / 9,
                        width / 2 - width / 9},
                new int[]{windowY + windowHeight, windowY + windowHeight, floorY, floorY}, 4);

        int dripX = width * 4 / 5;
        g.setColor(new Color(38, 47, 56));
        g.fillOval(dripX - width / 18, floorY - height / 16, width / 9, height / 24);
        g.setColor(new Color(159, 180, 198, 180));
        g.fillOval(dripX - Math.max(2, width / 260), height / 5, Math.max(4, width / 120),
                Math.max(6, height / 18));
        g.drawLine(dripX, height / 8, dripX, height / 5);
        if (ventVisible) {
            int ventWidth = width / 6;
            int ventHeight = Math.max(12, height / 8);
            int ventX = width / 2 - ventWidth / 2;
            int ventY = height - ventHeight - height / 18;
            g.setColor(new Color(16, 17, 18));
            g.fillRect(ventX, ventY, ventWidth, ventHeight);
            g.setColor(new Color(120, 120, 115));
            g.drawRect(ventX, ventY, ventWidth, ventHeight);
            for (int x = ventX + ventWidth / 8; x < ventX + ventWidth; x += Math.max(4, ventWidth / 7)) {
                g.drawLine(x, ventY + 2, x, ventY + ventHeight - 2);
            }
        }
        drawVignette(g, width, height);
        if (showCaption) {
            drawCaption(g, ventVisible ? "A DUSTY VENT" : "THE DIM ROOM");
        }
    }

    private void drawBoxes(Graphics2D g, int width, int height) {
        drawDimRoom(g, width, height, false, false);
        int baseY = height - height / 12;
        drawCrate(g, width / 2 + width / 10, baseY, width / 5, height / 3);
        drawCrate(g, width / 2 + width / 5, baseY - height / 7, width / 6, height / 4);
        drawCrate(g, width / 2 - width / 18, baseY - height / 4, width / 7, height / 4);
        drawCaption(g, "A PILE OF BOXES");
    }

    private void drawCellar(Graphics2D g, int width, int height) {
        fillBackground(g, width, height, new Color(11, 9, 9));
        g.setColor(new Color(71, 52, 39));
        int beamWidth = Math.max(5, width / 80);
        for (int x = width / 20; x < width; x += Math.max(24, width / 11)) {
            g.fillRect(x, height / 10, beamWidth, height * 8 / 10);
        }
        g.setColor(new Color(83, 61, 43));
        for (int y : new int[]{height / 10, height / 2, height * 9 / 10}) {
            g.fillRect(width / 20, y, width * 9 / 10, beamWidth);
        }
        g.setColor(new Color(31, 26, 22));
        g.fillRect(0, height * 3 / 4, width, height / 4);

        int bulbX = width / 2;
        int bulbY = height / 4;
        drawGlow(g, bulbX, bulbY, Math.max(28, height / 3), new Color(255, 176, 112, 50));
        g.setColor(new Color(35, 30, 25));
        g.setStroke(new BasicStroke(Math.max(2f, width / 350f)));
        g.drawLine(bulbX, height / 10, bulbX, bulbY - height / 30);
        g.setColor(new Color(255, 207, 138));
        int bulbSize = Math.max(5, height / 28);
        g.fillOval(bulbX - bulbSize / 2, bulbY - bulbSize / 2, bulbSize, bulbSize);

        int manX = width / 2;
        int headY = height / 3;
        int shoulderY = height / 2;
        int waistY = height * 3 / 4;
        g.setColor(new Color(133, 129, 123));
        g.setStroke(new BasicStroke(Math.max(3f, width / 250f),
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int headSize = Math.max(10, height / 10);
        g.drawOval(manX - headSize / 2, headY - headSize, headSize, headSize);
        g.drawLine(manX, headY, manX, waistY);
        g.drawLine(manX, shoulderY, manX - width / 12, shoulderY + height / 14);
        g.drawLine(manX, shoulderY, manX + width / 12, shoulderY + height / 14);
        g.drawLine(manX, waistY, manX - width / 18, height - height / 14);
        g.drawLine(manX, waistY, manX + width / 18, height - height / 14);
        g.setColor(new Color(160, 159, 150));
        g.setStroke(new BasicStroke(Math.max(1f, width / 700f)));
        drawChain(g, manX - width / 12, shoulderY + height / 14, width / 18, height / 3);
        drawChain(g, manX + width / 12, shoulderY + height / 14, width - width / 18, height / 3);
        drawVignette(g, width, height);
        drawCaption(g, "THE CELLAR");
    }

    private void drawCreature(Graphics2D g, int width, int height, boolean secondObjectVisible, Scene scene) {
        fillBackground(g, width, height, new Color(6, 9, 8));
        int centerX = width / 2;
        int bodyY = (int) (height * 0.57);
        int bodyWidth = width / 7;
        int bodyHeight = height / 3;
        g.setColor(new Color(0, 0, 0, 115));
        g.fillOval(centerX - bodyWidth, height - height / 8, bodyWidth * 2, height / 12);
        g.setPaint(new GradientPaint(centerX - bodyWidth, bodyY, new Color(48, 69, 45),
                centerX + bodyWidth, height, new Color(15, 27, 18)));
        g.fillOval(centerX - bodyWidth, bodyY, bodyWidth * 2, bodyHeight);
        g.fillPolygon(new int[]{centerX - bodyWidth / 2, centerX + bodyWidth / 2,
                        centerX + bodyWidth / 3, centerX - bodyWidth / 3},
                new int[]{height / 4, height / 4, height * 4 / 5, height * 4 / 5}, 4);
        g.setStroke(new BasicStroke(Math.max(5f, width / 100f),
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(38, 57, 37));
        g.drawLine(centerX - width / 22, height / 3, centerX - width / 8, height / 6);
        g.drawLine(centerX + width / 22, height / 3, centerX + width / 8, height / 6);
        g.drawLine(centerX - width / 8, height / 6, centerX - width / 6, height / 10);
        g.drawLine(centerX + width / 8, height / 6, centerX + width / 6, height / 10);
        g.drawLine(centerX - width / 20, height / 2, centerX - width / 5, height / 2 - height / 8);
        g.drawLine(centerX + width / 20, height / 2, centerX + width / 5, height / 2 - height / 8);
        g.drawLine(centerX - width / 5, height / 2 - height / 8,
                centerX - width / 4, height / 3);
        g.drawLine(centerX + width / 5, height / 2 - height / 8,
                centerX + width / 4, height / 3);
        g.setColor(new Color(80, 95, 57));
        g.drawLine(centerX - width / 6, height / 10, centerX - width / 5, height / 18);
        g.drawLine(centerX + width / 6, height / 10, centerX + width / 5, height / 18);
        g.setColor(new Color(109, 136, 83));
        g.setStroke(new BasicStroke(Math.max(1f, width / 400f)));
        g.drawArc(centerX - width / 24, height / 3, width / 12, height / 20, 190, 160);

        if (secondObjectVisible) {
            int objectX = width - width / 8;
            int objectY = height - height / 4;
            drawGlow(g, objectX, objectY, Math.max(20, height / 3),
                    new Color(160, 255, 230, 70));
            g.setColor(new Color(110, 200, 190, 75));
            g.fillOval(objectX - height / 8, objectY - height / 8, height / 4, height / 4);
            g.setColor(new Color(160, 255, 230));
            g.fillOval(objectX - height / 28, objectY - height / 28, height / 14, height / 14);
        }
        if (scene == Scene.GRAB_SECOND_OBJECT) {
            g.setColor(new Color(255, 245, 220, 48));
            g.fillRect(0, 0, width, height);
        }
        drawVignette(g, width, height);
        drawCaption(g, secondObjectVisible ? "THE SLEEPING BEAST" : "A CREATURE IN THE ROOM");
    }

    private void drawMineshaft(Graphics2D g, int width, int height, Scene scene) {
        fillBackground(g, width, height, new Color(9, 8, 7));
        int centerX = width / 2;
        int openingTop = height / 8;
        int openingWidth = width / 3;
        g.setColor(new Color(82, 61, 43));
        g.fillPolygon(new int[]{centerX - openingWidth / 2, centerX + openingWidth / 2,
                        width - width / 20, width / 20},
                new int[]{openingTop, openingTop, height, height}, 4);
        g.setPaint(new GradientPaint(0, openingTop, new Color(13, 11, 9),
                0, height, new Color(3, 3, 3)));
        g.fillOval(centerX - openingWidth / 2 + width / 30, openingTop + height / 24,
                openingWidth - width / 15, height - openingTop);
        g.setColor(new Color(111, 87, 61));
        g.setStroke(new BasicStroke(Math.max(4f, width / 110f),
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawArc(centerX - openingWidth / 2, openingTop, openingWidth, height / 2, 180, 180);
        g.setColor(new Color(86, 73, 57));
        g.setStroke(new BasicStroke(Math.max(2f, width / 300f)));
        g.drawLine(centerX - width / 25, height / 2, centerX - width / 8, height);
        g.drawLine(centerX + width / 25, height / 2, centerX + width / 8, height);
        for (int i = 0; i < 7; i++) {
            int depth = i + 1;
            int y = height / 2 + depth * height / 16;
            int span = width / 25 + depth * width / 50;
            g.drawLine(centerX - span, y, centerX + span, y);
        }
        g.setColor(new Color(95, 75, 53));
        g.setStroke(new BasicStroke(Math.max(4f, width / 120f)));
        g.drawLine(centerX - width / 5, openingTop + height / 6, centerX - width / 5, height);
        g.drawLine(centerX + width / 5, openingTop + height / 6, centerX + width / 5, height);
        g.drawLine(centerX - width / 5, openingTop + height / 6,
                centerX + width / 5, openingTop + height / 6);
        drawGlow(g, centerX, height / 2, Math.max(22, height / 3),
                new Color(255, 192, 112, 60));
        g.setColor(new Color(255, 220, 145));
        int lightSize = Math.max(6, height / 20);
        g.fillOval(centerX - lightSize / 2, height / 2 - lightSize / 2, lightSize, lightSize);
        if (scene == Scene.MINESHAFT_ENTER) {
            drawLanterns(g, width, height);
        }
        drawVignette(g, width, height);
        drawCaption(g, "A LIGHT DEEP IN THE MINESHAFT");
    }

    private void drawNightBackdrop(Graphics2D g, int width, int height, Color top, Color horizon) {
        g.setPaint(new GradientPaint(0, 0, top, 0, height, horizon));
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < 30; i++) {
            int x = Math.floorMod(i * 83 + 17, Math.max(1, width));
            int y = Math.floorMod(i * 47 + 11, Math.max(1, height / 2));
            g.setColor(new Color(210, 216, 205, 50 + i % 4 * 18));
            g.fillOval(x, y, i % 6 == 0 ? 2 : 1, i % 6 == 0 ? 2 : 1);
        }
        int moonX = width * 4 / 5;
        int moonY = height / 6;
        drawGlow(g, moonX, moonY, Math.max(12, height / 7), new Color(178, 191, 198, 55));
        g.setColor(new Color(222, 222, 207, 165));
        int moonSize = Math.max(5, height / 28);
        g.fillOval(moonX - moonSize / 2, moonY - moonSize / 2, moonSize, moonSize);
    }

    private void drawDoorShards(Graphics2D g, int width, int height) {
        int floorY = height * 3 / 4;
        g.setColor(new Color(169, 178, 187, 190));
        int[][] points = {
                {width / 3, width / 3 + width / 35, width / 3 + width / 18},
                {width / 2 - width / 20, width / 2 - width / 80, width / 2 + width / 25},
                {width * 2 / 3, width * 2 / 3 + width / 40, width * 2 / 3 + width / 20}
        };
        int[] heights = {height / 7, height / 5, height / 9};
        for (int i = 0; i < points.length; i++) {
            g.fillPolygon(points[i],
                    new int[]{floorY, floorY - heights[i], floorY - height / 35}, 3);
            g.setColor(new Color(236, 240, 244, 180));
            g.drawLine(points[i][1], floorY - heights[i], points[i][2], floorY - height / 35);
            g.setColor(new Color(169, 178, 187, 190));
        }
    }

    private void drawCrate(Graphics2D g, int x, int baseY, int crateWidth, int crateHeight) {
        int safeWidth = Math.max(12, crateWidth);
        int safeHeight = Math.max(10, crateHeight);
        int topY = baseY - safeHeight;
        g.setPaint(new GradientPaint(x - safeWidth / 2f, topY, new Color(140, 101, 63),
                x + safeWidth / 2f, baseY, new Color(65, 45, 31)));
        g.fillRect(x - safeWidth / 2, topY, safeWidth, safeHeight);
        g.setColor(new Color(172, 129, 83));
        g.setStroke(new BasicStroke(Math.max(1f, safeWidth / 50f)));
        g.drawRect(x - safeWidth / 2, topY, safeWidth, safeHeight);
        g.drawLine(x - safeWidth / 2, topY + safeHeight / 3,
                x + safeWidth / 2, topY + safeHeight / 3);
        g.drawLine(x - safeWidth / 2, topY + safeHeight * 2 / 3,
                x + safeWidth / 2, topY + safeHeight * 2 / 3);
        g.drawLine(x, topY, x, baseY);
    }

    private void drawChain(Graphics2D g, int startX, int startY, int endX, int endY) {
        int links = 10;
        int linkSize = Math.max(3, (int) (Math.abs(endX - startX) / (links * 4.0)));
        for (int i = 0; i <= links; i++) {
            double fraction = (double) i / links;
            int x = (int) Math.round(startX + (endX - startX) * fraction);
            int y = (int) Math.round(startY + (endY - startY) * fraction
                    + Math.sin(fraction * Math.PI) * Math.max(2, Math.abs(endX - startX) / 30));
            g.drawOval(x - linkSize / 2, y - linkSize / 2, linkSize, linkSize);
        }
    }

    private void drawLanterns(Graphics2D g, int width, int height) {
        int baseY = height * 4 / 5;
        int[] offsets = {-width / 9, -width / 20, width / 15, width / 8};
        for (int i = 0; i < offsets.length; i++) {
            int x = width / 2 + offsets[i];
            int jarHeight = Math.max(8, height / (i % 2 == 0 ? 12 : 15));
            int jarWidth = Math.max(5, jarHeight * 2 / 3);
            drawGlow(g, x, baseY - jarHeight / 2, jarHeight,
                    new Color(230, 194, 106, 45));
            g.setColor(new Color(205, 173, 101, 170));
            g.fillRoundRect(x - jarWidth / 2, baseY - jarHeight, jarWidth, jarHeight, 3, 3);
            g.setColor(new Color(154, 59, 42));
            g.fillRect(x - jarWidth / 2, baseY - jarHeight, jarWidth, Math.max(2, jarHeight / 7));
        }
    }

    private void drawGlow(Graphics2D g, double centerX, double centerY, double radius, Color color) {
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

    private void drawVignette(Graphics2D g, int width, int height) {
        Paint previousPaint = g.getPaint();
        float radius = Math.max(1f, (float) (Math.max(width, height) * 0.78));
        g.setPaint(new RadialGradientPaint(
                new Point2D.Float(width / 2f, height / 2f), radius,
                new float[]{0f, 0.65f, 1f},
                new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 18),
                        new Color(0, 0, 0, 130)}));
        g.fillRect(0, 0, width, height);
        g.setPaint(previousPaint);
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
