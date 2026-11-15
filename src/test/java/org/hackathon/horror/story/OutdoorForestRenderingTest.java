package org.hackathon.horror.story;

import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class OutdoorForestRenderingTest {
    @Test
    void forestRendersAtApplicationAndDisplaySizes() {
        int[][] sizes = {{500, 190}, {1032, 190}, {1366, 768}};
        OutdoorScenePainter painter = new OutdoorScenePainter();

        for (int[] size : sizes) {
            int width = size[0];
            int height = size[1];
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            try {
                painter.paint(graphics, width, height, Scene.FOREST_WAIT);
            } finally {
                graphics.dispose();
            }

            assertNotEquals(image.getRGB(0, height - 1),
                    image.getRGB(width / 2, height - 1));
            int vanishingY = Math.max(18, Math.min(height - 24, (int) (height * 0.34)));
            int farPathHalfWidth = Math.max(4, width / 90);
            assertNotEquals(image.getRGB(width / 2, vanishingY + 2),
                    image.getRGB(width / 2 + farPathHalfWidth + 10, vanishingY + 2));
        }
    }
}
