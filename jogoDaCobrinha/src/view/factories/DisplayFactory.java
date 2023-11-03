package view.factories;

import model.Fruit;
import model.Physical;
import model.Snake;
import view.Display;
import view.displays.DisplayBlockMethod;
import view.displays.DisplayFruitMethod;
import view.displays.DisplaySnakeMethod;

public class DisplayFactory {
    enum DisplayObject {
        Block, Snake, Fruit
    }

    public static Display create(Physical object) {
        DisplayObject z = DisplayObject.valueOf(object.getClass().getSimpleName());

        return switch (z) {
            case Block -> DisplayBlockMethod.create(object);
            case Snake -> DisplaySnakeMethod.create((Snake) object);
            case Fruit -> DisplayFruitMethod.create((Fruit) object);
        };
    }
}
