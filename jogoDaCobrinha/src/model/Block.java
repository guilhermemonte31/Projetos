package model;

import resources.Settings;

import java.util.List;

public class Block extends Physical {
    public Block(int x, int y) {
        super(x * Settings.GRID_UNIT_SIZE, y * Settings.GRID_UNIT_SIZE);
        width = Settings.GRID_UNIT_SIZE;
        height = Settings.GRID_UNIT_SIZE;
    }

    @Override
    public void checkCollision(List<Physical> objects) {
        /* todo */
    }

    @Override
    public void onCollision(Physical object) {
        /* todo */
    }
}
