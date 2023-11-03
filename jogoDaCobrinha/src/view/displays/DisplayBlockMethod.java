package view.displays;

import model.Physical;
import resources.Settings;
import view.Display;

import java.awt.*;

public class DisplayBlockMethod {
    static final int padding = (int)(1.0 * Settings.GRID_UNIT_SIZE / 30.0);

    public static Display create(Physical object) {
        return new Display(
            object,
            (graphics)-> drawBlock(graphics, object),
            (int)object.getX(),
            (int)object.getY(),
            (int)object.getWidth(),
            (int)object.getHeight()
        );
    }

    private static void drawBlock(Graphics2D graphics, Physical object) {
        graphics.setColor(new Color(0xb7b7b7));
        graphics.fillRect(
            (int)object.getX(),
            (int)object.getY(),
            (int)object.getWidth(),
            (int)object.getHeight()
        );

        setBrush(graphics);
        graphics.setColor(new Color(0xebebeb));
        for (int i = 0; i < 5; i++) {
            graphics.drawLine(
                (int)(object.getX() + padding/2 + i),
                (int)(object.getY() + padding/2 + i),
                (int)(object.getX() + object.getWidth() - padding/2 - i),
                (int)(object.getY() + padding/2 + i)
            );
        }

        graphics.setColor(new Color(0x808080));
        for (int i = 0; i < 5; i++) {
            graphics.drawLine(
                (int)(object.getX() + padding/2 + i),
                (int)(object.getY() + object.getHeight() - padding/2 - i),
                (int)(object.getX() + object.getWidth() - padding/2 - i),
                (int)(object.getY() + object.getHeight() - padding/2 - i)
            );
        }

        graphics.setColor(new Color(0x9c9c9c));
        for (int i = 0; i < 5; i++) {
            graphics.drawLine(
                (int)(object.getX() + padding/2 + i),
                (int)(object.getY() + padding/2 + i),
                (int)(object.getX() + padding/2 + i),
                (int)(object.getY() + object.getHeight() - padding/2 - i)
            );
        }

        graphics.setColor(new Color(0xd0d0d0));
        for (int i = 0; i < 5; i++) {
            graphics.drawLine(
                (int)(object.getX() + object.getWidth() + padding/2 - i),
                (int)(object.getY() + padding/2 + i),
                (int)(object.getX() + object.getWidth() + padding/2 - i),
                (int)(object.getY() + object.getHeight() - padding/2 - i)
            );
        }
    }

    private static void setBrush(Graphics2D graphics) {
        BasicStroke stroke = new BasicStroke(DisplayBlockMethod.padding, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
        graphics.setStroke(stroke);
    }
}
