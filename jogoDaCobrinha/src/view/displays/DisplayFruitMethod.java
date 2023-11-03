package view.displays;

import model.Fruit;
import model.Physical;
import resources.Settings;
import view.Display;

import java.awt.*;

public class DisplayFruitMethod {
    public static final int OFFSIDE_X = (int)(Settings.GRID_UNIT_SIZE /4);
    public static final int OFFSIDE_Y = (int)(12 * Settings.GRID_UNIT_SIZE /30);

    public static Display create(Fruit object) {
        return new Display(
                object,
                (graphics)-> drawFruit(graphics, object),
                (int)object.getX(),
                (int)object.getY(),
                (int)object.getWidth(),
                (int)object.getHeight()
        );
    }

    private static void drawFruit(Graphics2D graphics, Fruit object) {
        final Image img = switch (object.getType()) {
            case NORMAL -> Settings.Images.APPLE.value;
            case CHERRY -> Settings.Images.CHERRY.value;
            case STRAWBERRY -> Settings.Images.STRAWBERRY.value;
            case GOLDEN -> Settings.Images.GOLDEN_APPLE.value;
        };

        graphics.setColor(Settings.Colors.TRANSPARENT_SHADOW.value);
        graphics.fillOval(
                (int) object.getX() + OFFSIDE_X,
                (int) object.getY() + OFFSIDE_Y,
                (int) object.getWidth() / 2,
                (int) object.getHeight() / 2
        );
        graphics.drawImage(
                img,
                (int)(object.getX()),
                (int)(object.getY()),
                (int)(object.getWidth()),
                (int)(object.getHeight()),
                null
        );
    }
}
