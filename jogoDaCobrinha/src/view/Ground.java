package view;

import resources.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ground {
    public static BufferedImage imgTerrain;

    public static void generateTerrain(int groundUnitSize, int width, int height) {
        imgTerrain = new BufferedImage(
            groundUnitSize * width,
            groundUnitSize * height,
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) imgTerrain.getGraphics();

        graphics.setColor(Settings.Colors.BACKGROUND.value);
        graphics.fillRect(0, 0, groundUnitSize * width, groundUnitSize * height);

        /* Fill grid */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((i + j) % 2 == 0) {
                    graphics.setColor(Settings.Colors.BACKGROUND_DARKER.value);
                    graphics.fillRect(i * groundUnitSize, j * groundUnitSize, groundUnitSize, groundUnitSize);
                }
            }
        }
    }
}
