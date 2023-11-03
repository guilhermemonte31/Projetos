package model;

import controller.Controller;
import resources.Settings;
import resources.Settings.Direction;
import resources.Sound;
import resources.Utils;
import utils.Pair;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Snake extends Physical {
    public static final double SNAKE_BASE_SIZE = (1.0 * Settings.GRID_UNIT_SIZE) / 30;
    public static final int SNAKE_BODY_SEGMENT_SIZE = (30 * Settings.GRID_UNIT_SIZE / 30);
    public static final int SNAKE_THICKNESS = (int)(20 * Settings.GRID_UNIT_SIZE / 30);
    public static final int SNAKE_OFFSET_GRID_X = 0;
    public static final int SNAKE_OFFSET_GRID_Y = 0;

    /* Score */
    int snakeScore = 0;

    /* Body length without counting with head */
    int snakeBodyLength = 1 * SNAKE_BODY_SEGMENT_SIZE;
    int snakeSpeed = 1;

    Pair firstSegmentPosition;
    int firstSegmentPositionX;
    int firstSegmentPositionY;
    int lastSegmentPositionX;
    int lastSegmentPositionY;

    boolean removeFirst=false;
    boolean removeLast=false;

    LinkedList<BodySegment> bodySegments = new LinkedList<>();
    boolean isDead = false;

    Direction direction = Direction.NONE;
    public Color headColor;
    public Color tailColor;

    Controller.ControlType control;

    public Snake(double gridX, double gridY, Direction direction, Color headColor, Color tailColor, Controller.ControlType control) {
        super(
        gridX * Settings.GRID_UNIT_SIZE + SNAKE_OFFSET_GRID_X + (1.0 * SNAKE_THICKNESS) / 4,
        gridY * Settings.GRID_UNIT_SIZE + SNAKE_OFFSET_GRID_Y + (1.0 * SNAKE_THICKNESS) / 4
        );

        this.control = control;
        setInitialPositionsByDirection(direction);
        System.out.println(lastSegmentPositionX);
        System.out.println(lastSegmentPositionY);

        this.headColor = headColor;
        this.tailColor = tailColor;

        bodySegments.add(new BodySegment(snakeBodyLength, direction));
    }

    public void setInitialPositionsByDirection(Direction direction) {
        switch (direction) {
            case RIGHT -> {
                firstSegmentPositionX = (int)x;
                firstSegmentPositionY = (int)y;
                lastSegmentPositionX = (int)x + getSnakeBodyLength() + SNAKE_THICKNESS /2;
                lastSegmentPositionY = (int)y - SNAKE_THICKNESS /2;
            }
            case LEFT -> {
                firstSegmentPositionX = (int)x - SNAKE_THICKNESS;
                firstSegmentPositionY = (int)y;
                lastSegmentPositionX = (int)x - getSnakeBodyLength() - SNAKE_THICKNESS /2;
                lastSegmentPositionY = (int)y - SNAKE_THICKNESS /2;
            }
        }
    }

    public int getSnakeBodyLength() {
        return snakeBodyLength;
    }

    public LinkedList<BodySegment> getBodySegments() { return this.bodySegments; }

    public boolean isDead() { return isDead; }

    public int getFirstSegmentPositionX() { return firstSegmentPositionX; }

    public int getFirstSegmentPositionY() { return firstSegmentPositionY; }

    public int getLastSegmentPositionX() { return lastSegmentPositionX; }

    public int getLastSegmentPositionY() { return lastSegmentPositionY; }

    public void move(Direction direction) {
        if (
            bodySegments.get(bodySegments.size()-1).getDirection() != direction &&
            bodySegments.get(bodySegments.size()-1).getDirection() != direction.opposite()
        ) {
            this.direction = direction;
        }
    }

    public void coordinate(int direction) {
        if (bodySegments.size() != 1) {
            int i = bodySegments.size() - 1;
            bodySegments.get(i).loop(BodySegment.ADD * direction);
            bodySegments.get(0).loop(BodySegment.SUB * direction);
        }
    }

    public void moveSnake(int speed) {
        if (!isDead) {
            coordinate(speed > 0 ? 1 : - 1);
            moveHead(speed);
            moveTail(speed);
        }
    }

    public void moveHead(int speed) {
        firstSegmentPositionX = Utils.incrementValueByDirection(
            bodySegments.get(0).getDirection(),
            Direction.LEFT, firstSegmentPositionX, speed
        );
        firstSegmentPositionY = Utils.incrementValueByDirection(
            bodySegments.get(0).getDirection(),
            Direction.UP, firstSegmentPositionY, speed
        );
    }

    public void moveTail(int speed) {
        lastSegmentPositionX = Utils.incrementValueByDirection
            (bodySegments.getLast().getDirection(),
            Direction.LEFT, lastSegmentPositionX, speed
            );
        lastSegmentPositionY = Utils.incrementValueByDirection(
            bodySegments.getLast().getDirection(),
            Direction.UP, lastSegmentPositionY, speed
        );
    }

    @Override
    public void loop() {
        if (Game.isRunning) {
            loopNormal();
        }
    }

    public void loopNormal() {
        checkMovement();

        if (removeFirst) {
            bodySegments.remove(0);
            removeFirst = false;
        }

        moveSnake(snakeSpeed);
    }

    public void checkMovement() {
        boolean go = false;
        switch (direction) {
            case LEFT, RIGHT -> {
                if ((lastSegmentPositionY)%Settings.GRID_UNIT_SIZE == SNAKE_OFFSET_GRID_X) {
                    go = true;
                }
            }
            case UP, DOWN -> {
                if ((lastSegmentPositionX)%Settings.GRID_UNIT_SIZE == SNAKE_OFFSET_GRID_Y) {
                    go = true;
                }
            }
        }
        if (go) {
            bodySegments.get(0).length -= snakeSpeed;
            moveHead(snakeSpeed);
            bodySegments.add(new BodySegment(snakeSpeed, direction));
            moveTail(snakeSpeed);
            direction = Direction.NONE;
        }
    }

    @Override
    public void checkCollision(List<Physical> objects) {
        for (Physical object : objects) {
            if (object.getClass() == Snake.class) {
                if (object == this)
                    checkCollisionItself();
            } else {
                int centerPosX = this.lastSegmentPositionX - SNAKE_THICKNESS;
                int centerPosY = this.lastSegmentPositionY + SNAKE_THICKNESS/2;

                if (
                    Utils.checkCollision(
                        centerPosX, centerPosY,
                        (int)(object.x+object.width/2),
                        (int)(object.y+object.height/2)
                    )
                ) {
                    System.out.println("collision triggered");
                    object.onCollision(this);
                    this.onCollision(object);
                }
            }
        }
    }

    public void checkCollisionItself() {
//        Direction direction = bodySegments.getLast().getDirection();
//        int centerPosX = Utils.incrementValueByDirection(
//            direction, Direction.LEFT, lastSegmentPositionX - SNAKE_THICKNESS /2, SNAKE_THICKNESS /4);
//        int centerPosY = Utils.incrementValueByDirection(
//            direction, Direction.UP, lastSegmentPositionY + SNAKE_THICKNESS /2, SNAKE_THICKNESS /4);

        int centerPosX = this.lastSegmentPositionX - SNAKE_THICKNESS/4;
        int centerPosY = this.lastSegmentPositionY + SNAKE_THICKNESS/4;

        int lastPosX = firstSegmentPositionX;
        int lastPosY = firstSegmentPositionY;
        int newPosX;
        int newPosY;

        int numberOfSegments = bodySegments.size() - 1;
        int num = -1;

        for (BodySegment bodySegment : bodySegments) {
            num++;

            newPosX = Utils.incrementValueByDirection(
                bodySegment.getDirection(), Direction.LEFT, lastPosX, bodySegment.length);
            newPosY = Utils.incrementValueByDirection(
                bodySegment.getDirection(), Direction.UP, lastPosY, bodySegment.length);

            if (
                Utils.checkCollision(
                    centerPosX, centerPosY,
                    lastPosX, lastPosY,
                    newPosX, newPosY
                ) && (numberOfSegments - 1) > num) {
                onCollision(this);
            }

            lastPosX = newPosX;
            lastPosY = newPosY;
        }
    }

    @Override
    public void onCollision(Physical object) {
        if (object.getClass() == Fruit.class) {
            snakeScore += ((Fruit) object).getType().score;
            growth(SNAKE_BODY_SEGMENT_SIZE);
        } else if (object.getClass() == Block.class) {
            isDead = true;
            Game.isRunning = false;
        } else if (object.getClass() == Snake.class) {
            if (object == this) {
                System.out.println("collision with itself");
            } else {
                System.out.println("collision with another snake");
            }
            isDead = true;
            Game.isRunning = false;
        }
    }

    public void growth(int value) {
        if (Game.isRunning) {
            firstSegmentPositionX = Utils.incrementValueByDirection(bodySegments.get(0).getDirection(), Direction.LEFT, firstSegmentPositionX, -value);
            firstSegmentPositionY = Utils.incrementValueByDirection(bodySegments.get(0).getDirection(), Direction.UP, firstSegmentPositionY, -value);
            bodySegments.get(0).length += value;
            snakeBodyLength = getSnakeBodyLength() + value;
        }
    }

    public class BodySegment {
        int length;
        Direction direction;

        static final int SUB = -1;
        static final int ADD = 1;

        public BodySegment(int length, Direction direction) {
            this.length = length;
            this.direction = direction;
        }

        public Direction getDirection() { return this.direction; }

        public int getLength() { return this.length; }

        public void loop(int arg) {
            if (arg == SUB) {
                length -= Math.abs(snakeSpeed);
            }  else if (arg == ADD) {
                length += Math.abs(snakeSpeed);
            }

            if (length <= 0) {
                if (snakeSpeed > 0) { removeFirst = true; }
                else { length = 11; }
            }
        }
    }

    public int getScore(){
        return snakeScore;
    }
}
