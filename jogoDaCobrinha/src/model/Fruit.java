package model;

import resources.Settings;
import resources.Sound;
import resources.Utils;

import java.util.List;

public class Fruit extends Physical {
    public static final int SIZE = (35 * Settings.GRID_UNIT_SIZE /30);

    public enum Type {
        NORMAL(1), CHERRY(3), STRAWBERRY(5), GOLDEN(10);
        final int score;
        Type(int score) { this.score = score; }
    }

    private final Type type;

    public Fruit(int gridX, int gridY, Type type) {
        super(gridX * Settings.GRID_UNIT_SIZE, gridY * Settings.GRID_UNIT_SIZE);
        this.type = type;
        this.width = SIZE;
        this.height = SIZE;
    }

    public Type getType() { return type; }

    public int getScore() { return getType().score; }

    @Override
    public void onCollision(Physical object) {
        if (object.getClass() == Snake.class) {
            this.removeFromGame();
            Sound.COIN.play();
            Game.generateFruits(1);
        }
    }

    @Override
    public void checkCollision(List<Physical> objects) {}

    public boolean invalidPosition(int x, int y, List<Physical> objects){
        boolean a = false, b = false, c = false;
        for (Physical object : objects) {
            if (object.getClass() == Snake.class) {
                continue;
            } else if (object.getClass() == Block.class) {
                b = checkBlockCollision(x, y,(Block)object);
            }
            else{
                c = checkFruitCollision(x, y,(Fruit) object);
            }
            if(a || b || c) return true;
        }
        return false;
    }

    private boolean checkBlockCollision(int x, int y, Block block){
        return x == (int) block.x && y == (int) block.y;
    }
    private boolean checkFruitCollision(int x, int y, Fruit fruit){
        return x == (int) fruit.x && y == (int) fruit.y;
    }
}
