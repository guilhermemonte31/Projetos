package model;

import java.util.List;

public abstract class Physical {
    public double x, y;
    public double vx, vy = 0;
    public double width, height;

    public Physical(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void loop() {
        x += this.vx;
        y += this.vy;
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setSpeed(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void removeFromGame() { Game.removeObject(this); }

    public abstract void checkCollision(List<Physical> objects);

    public abstract void onCollision(Physical object);
}
