package view.displays;

import model.Snake;
import resources.Settings;
import resources.Settings.Direction;
import resources.Utils;
import view.Display;

import java.awt.*;
import java.util.LinkedList;

public class DisplaySnakeMethod {
    static final int SIZE = (int)(20 * Snake.SNAKE_BASE_SIZE);
    static final int BORDER = 0;

    static final int EYE_SPACE = 0;
    static final int EYE_ROUND_SIZE = (int)(5 * Snake.SNAKE_BASE_SIZE);
    static final int EYE_SIZE = (int)(8 * Snake.SNAKE_BASE_SIZE);
    static final int EYE_DISTANCE = (int)(5 * Snake.SNAKE_BASE_SIZE);
    static final int PUPIL_SIZE = (int)(4 * Snake.SNAKE_BASE_SIZE);
    static final int REFLECTION_SIZE = (int)(2 * Snake.SNAKE_BASE_SIZE);

    static final int NOSE_DISTANCE = (int)(3 * Snake.SNAKE_BASE_SIZE);
    static final int NOSE_DISTANCE_2 = (int)(5 * Snake.SNAKE_BASE_SIZE);

    static final int SHADOW_WIDTH = (int)(6 * Snake.SNAKE_BASE_SIZE);
    static final int DIRECTION_SIZE = (int)(3 * Snake.SNAKE_BASE_SIZE);

    static final int OFFSIDE_X = (int)(Snake.SNAKE_THICKNESS /4);
//    static final int OFFSIDE_X = 0;
    static final int OFFSIDE_Y = (int)(Snake.SNAKE_THICKNESS /4);
//    static final int OFFSIDE_Y = 0;

    public static Display create(Snake object) {
        return new Display(
            object,
            (graphics)-> drawSnake(graphics, object),
            (int)object.getX(),
            (int)object.getY(),
            (int)object.getWidth(),
            (int)object.getHeight()
        );
    }

    private static void drawSnake(Graphics2D graphics, Snake object) {
        setBrush(graphics);

        /* DRAW THE SHADOW */
        graphics.setColor(Settings.Colors.SHADOW.value);
        double[] result = drawBody(
            graphics,
            false,
            SHADOW_WIDTH,

            object,
            object.getFirstSegmentPositionX(),
            object.getFirstSegmentPositionY(),
            object.getBodySegments()
        );
        drawEyeBall(graphics, (int)result[0], (int)result[2], (int)result[1], (int)result[3], SHADOW_WIDTH);

        /* DRAW BODY AND EYES */
        drawBody(
            graphics,
            true,
            0,
            object,
            object.getFirstSegmentPositionX(),
            object.getFirstSegmentPositionY(),
            object.getBodySegments()
        );
        drawEyeBall(graphics, (int)result[0], (int)result[2], (int)result[1], (int)result[3], 0);
        drawEyes(graphics,
            (int)result[0], (int)result[2], (int)result[1],
            (int)result[3], (int)result[4], (int)result[5],
            object.isDead()
        );
    }

    private static void setBrush(Graphics2D graphics) {
        BasicStroke stroke = new BasicStroke(SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        graphics.setStroke(stroke);
    }

    public static void changeColor(
        Graphics2D graphics,
        boolean colorChangingEnabled,
        Snake.BodySegment bodyMember,
        Snake snake,
        int snakeLengthDone,
        int lpDrawX, int lpDrawY,
        int npDrawX, int npDrawY
    ) {
        if (!colorChangingEnabled) return;

        Color color1 = Utils.getColorGradient(
            snake.getSnakeBodyLength(),
            snakeLengthDone - bodyMember.getLength(),
            snake.tailColor,
            snake.headColor
        );
        Color color2 = Utils.getColorGradient(
            snake.getSnakeBodyLength(),
            snakeLengthDone,
            snake.tailColor,
            snake.headColor
        );

        GradientPaint gp = new GradientPaint(
            lpDrawX, lpDrawY,
            color1,
            npDrawX, npDrawY,
            color2
        );
        graphics.setPaint(gp);
    }

    /**
     * Draw the body of the snake
     *
     * @param graphics      Graphics2D
     * @param snake   Snake
     * @param firstSegmentPositionX initial pos x to draw
     * @param firstSegmentPositionY initial pos y to draw
     * @param bodyMembers   Snake Body Members
     */
    private static double[] drawBody(
        Graphics2D graphics,
        boolean changingColorEnabled,
        int lns,
        Snake snake,
        int firstSegmentPositionX,
        int firstSegmentPositionY,
        LinkedList<Snake.BodySegment> bodyMembers
    ) {
        int lpDrawX = firstSegmentPositionX - OFFSIDE_X;
        int lpDrawY =  firstSegmentPositionY + OFFSIDE_Y;
        int npDrawX = firstSegmentPositionX - OFFSIDE_X;
        int npDrawY =  firstSegmentPositionY + OFFSIDE_Y;

        int snakeLengthDone = 0;
        for (Snake.BodySegment bodyMember : bodyMembers) {
            snakeLengthDone += bodyMember.getLength();

            npDrawX = Utils.incrementValueByDirection(
                bodyMember.getDirection(),
                Direction.LEFT,
                lpDrawX,
                bodyMember.getLength()
            );
            npDrawY = Utils.incrementValueByDirection(
                bodyMember.getDirection(),
                Direction.UP,
                lpDrawY,
                bodyMember.getLength()
            );

            changeColor(
                graphics,
                changingColorEnabled,
                bodyMember,
                snake,
                snakeLengthDone,
                lpDrawX, lpDrawY, npDrawX, npDrawY
            );

            graphics.drawLine(lpDrawX, lpDrawY + lns, npDrawX, npDrawY + lns);

            lpDrawX = npDrawX;
            lpDrawY = npDrawY;
        }

        Direction d = bodyMembers.get(bodyMembers.size() - 1).getDirection();
        double xb= Utils.incrementValueByDirection(d, npDrawX, -EYE_DISTANCE,-(EYE_SIZE),-1,1);
        double yb= Utils.incrementValueByDirection(d, npDrawY, -EYE_SIZE,-EYE_DISTANCE,1,-1);
        double x2b= Utils.incrementValueByDirection(d, npDrawX, -EYE_DISTANCE,(EYE_SIZE),-1,1);
        double y2b= Utils.incrementValueByDirection(d, npDrawY, EYE_SIZE,-EYE_DISTANCE,1,-1);
        int dirX = Utils.incrementValueByDirection(d, Direction.LEFT,0, DIRECTION_SIZE);
        int dirY = Utils.incrementValueByDirection(d, Direction.UP,0, DIRECTION_SIZE);

        return new double[] { xb, yb, x2b, y2b, dirX, dirY};
    }

    public static void drawEyeBall(Graphics2D graphics, int xb, int x2b, int yb, int y2b, int lns) {
        graphics.fillOval((int)xb-EYE_SIZE,(int)yb-EYE_SIZE+lns,EYE_SIZE*2,EYE_SIZE*2);
        graphics.fillOval((int)x2b-EYE_SIZE,(int)y2b-EYE_SIZE+lns,EYE_SIZE*2,EYE_SIZE*2);
    }

    public static void drawEyes(Graphics2D g, int xb, int x2b,int yb, int y2b, int dirX, int dirY, boolean eyeClosed) {
        if (!eyeClosed) {
            g.setColor(Color.white);
            g.fillOval((int)xb-EYE_ROUND_SIZE,(int)yb-EYE_ROUND_SIZE,EYE_ROUND_SIZE*2,EYE_ROUND_SIZE*2);
            g.fillOval((int)x2b-EYE_ROUND_SIZE,(int)y2b-EYE_ROUND_SIZE,EYE_ROUND_SIZE*2,EYE_ROUND_SIZE*2);
            g.setColor(Settings.Colors.PUPIL.value);
            g.fillOval((int)xb+dirX-PUPIL_SIZE,(int)yb+dirY-PUPIL_SIZE,PUPIL_SIZE*2,PUPIL_SIZE*2);
            g.fillOval((int)x2b+dirX-PUPIL_SIZE,(int)y2b+dirY-PUPIL_SIZE,PUPIL_SIZE*2,PUPIL_SIZE*2);
            g.setColor(Color.white);
            g.fillOval((int)xb+dirX,(int)yb+dirY-REFLECTION_SIZE,REFLECTION_SIZE,REFLECTION_SIZE);
            g.fillOval((int)x2b+dirX,(int)y2b+dirY-REFLECTION_SIZE,REFLECTION_SIZE,REFLECTION_SIZE);
        }
        else {
            g.setColor(Settings.Colors.EYELID.value);
            g.setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
            g.drawLine((int)xb-EYE_SIZE/2, (int)yb-EYE_SIZE/2, (int)xb+EYE_SIZE/2, (int)yb+EYE_SIZE/2);
            g.drawLine((int)xb+EYE_SIZE/2, (int)yb-EYE_SIZE/2, (int)xb-EYE_SIZE/2, (int)yb+EYE_SIZE/2);
            g.drawLine((int)x2b-EYE_SIZE/2, (int)y2b-EYE_SIZE/2, (int)x2b+EYE_SIZE/2, (int)y2b+EYE_SIZE/2);
            g.drawLine((int)x2b+EYE_SIZE/2, (int)y2b-EYE_SIZE/2, (int)x2b-EYE_SIZE/2, (int)y2b+EYE_SIZE/2);
        }
        g.setColor(Settings.Colors.NOSE.value);
        g.fillOval((int)xb+(int)(dirX*NOSE_DISTANCE)+(dirY==0?0:NOSE_DISTANCE_2),(int)yb+(int)(dirY*NOSE_DISTANCE)+(dirX==0?0:NOSE_DISTANCE_2),1*2,1*2);
        g.fillOval((int)x2b+(int)(dirX*NOSE_DISTANCE)+(dirY==0?0:-NOSE_DISTANCE_2),(int)y2b+(int)(dirY*NOSE_DISTANCE)+(dirX==0?0:-NOSE_DISTANCE_2),1*2,1*2);
    }
}
