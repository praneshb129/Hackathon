package org.hackathon.horror.story;

import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SceneArtworkRenderingTest {
    private static final Scene[] OUTDOOR_SCENES = {
            Scene.START, Scene.GATES, Scene.HATCHET_FIGHT, Scene.FOREST_DASH,
            Scene.FOREST_LEAVE_NOW, Scene.FOREST_WAIT, Scene.FOREST_LEAVE_QUIETLY,
            Scene.FOREST_BREAK_RUN, Scene.CROSS_STREET, Scene.WALK, Scene.KEEP_SPRINTING,
            Scene.LEFT_ROAD, Scene.RUN, Scene.KEEP_RUNNING, Scene.WOODS_PATH_CHOICE
    };

    private static final Scene[] INDOOR_SCENES = {
            Scene.MANOR_DOOR, Scene.DOOR_KICKED, Scene.ENTER_MANOR,
            Scene.DARK_ROOM_WITH_LAMP, Scene.DARK_ROOM_WITHOUT_LAMP, Scene.STEP_OVER_SHARDS,
            Scene.WALK_AROUND_SHARDS, Scene.DIM_ROOM, Scene.BOXES_CHOICE, Scene.CELLAR,
            Scene.CELLAR_CAUGHT, Scene.VENT_DISCOVERY, Scene.VENT_CREATURE_CHOICE,
            Scene.CREATURE_ROOM, Scene.GRAB_SECOND_OBJECT, Scene.MINESHAFT_ENTER,
            Scene.MINESHAFT_IGNORE
    };

    @Test
    void allSceneArtPaintsAtReferenceAndDisplaySizes() {
        int[][] sizes = {{500, 190}, {1366, 768}};
        OutdoorScenePainter outdoorPainter = new OutdoorScenePainter();
        IndoorScenePainter indoorPainter = new IndoorScenePainter();

        for (int[] size : sizes) {
            BufferedImage image = new BufferedImage(size[0], size[1], BufferedImage.TYPE_INT_ARGB);
            for (Scene scene : OUTDOOR_SCENES) {
                paint(image, graphics -> outdoorPainter.paint(graphics, size[0], size[1], scene));
            }
            for (Scene scene : INDOOR_SCENES) {
                paint(image, graphics -> indoorPainter.paint(graphics, size[0], size[1], scene, size[0] / 2));
            }
        }
    }

    private void paint(BufferedImage image, Painter painter) {
        Graphics2D graphics = image.createGraphics();
        try {
            painter.paint(graphics);
            assertEquals(255, (image.getRGB(0, 0) >>> 24) & 0xff);
        } finally {
            graphics.dispose();
        }
    }

    @FunctionalInterface
    private interface Painter {
        void paint(Graphics2D graphics);
    }
}
