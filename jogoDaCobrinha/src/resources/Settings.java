package resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Settings {
    public static final int GRID_UNIT_SIZE = 30;
    public static final int GRID_WIDTH = 20;
    public static final int GRID_HEIGHT = 20;

    public enum Direction {
        NONE(0),
        LEFT(-1),
        UP(-1),
        DOWN(1),
        RIGHT(1);

        final int multiplier;
        private Direction(int mult) {
            multiplier = mult;
        }
        public Direction opposite() {
            return switch (this) {
                case LEFT -> RIGHT;
                case RIGHT -> LEFT;
                case UP -> DOWN;
                case DOWN -> UP;
                default -> NONE;
            };
        }
    }

    public enum SnakeColors {
        TAIL_PURPLE(0x8579ff),
        HEAD_PURPLE(0Xbbaaff),
        TAIL_GREEN(0x64f53a),
        HEAD_GREEN(0x5ad04d)
        ;
        public final Color value;
        private SnakeColors (int hexColor) { this.value = new Color(hexColor); }
        private SnakeColors (Color value) { this.value = value; }
    }

    /** HEXADECIMAL COLOR CODES */
    public enum Colors {
        BACKGROUND(0xfbed93), // yellow
        BACKGROUND_DARKER(0xf5e581), // yellow darker
        BLUE_LIGHT(0x0fc3e8),
        BLUE_NORMAL(0x0194be),
        CYAN_LIGHT(0x65cfc8),
        CYAN_NORMAL(0x4cbdb6),
        GRAY_LIGHT(0xdde2e5),
        GRAY_NORMAL(0x98a9b2),
        GRAY_DARK(0x768c98),
        BLACK(0x424242),
        SNAKE_TAIL(0x8579ff),
        SNAKE_HEAD(0Xbbaaff),
        SHADOW(0xe6d986),
        PUPIL(0x162753),
        NOSE(0x666666),
        EYELID(0x3b991e),
        TRANSPARENT_SHADOW(new Color(80,55,25,40))
        ;

        public final Color value;
        private Colors (int hexColor) { this.value = new Color(hexColor); }
        private Colors (Color value) { this.value = value; }
    }

    public enum Images {
        APPLE("apple"),
        CHERRY("cherry"),
        STRAWBERRY("strawberry"),
        GOLDEN_APPLE("golden-apple");


        public Image value;
        private Images(String name) {
            try {
                value = ImageIO.read(
                        new File("res/" + name + ".png")
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
