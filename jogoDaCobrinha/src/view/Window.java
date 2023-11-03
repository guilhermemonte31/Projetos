package view;

import model.Game;
import resources.Settings;

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Window extends JFrame implements Runnable {
    /* difference between the real size of the windows screen and the settings constants */
    public static int differenceHeight;
    public static int differenceWidth;


    public static Camera camera;
    public static Thread gameThread;

    public Window() {
        super();

        Ground.generateTerrain(Settings.GRID_UNIT_SIZE, Settings.GRID_WIDTH, Settings.GRID_HEIGHT);
        initCamera();
        getDimensionDifferences();
        addWindowGraphics();
        addEventHandler();

        Game.initScene();

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            try {
                Game.loop();
                camera.repaint();
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addWindowGraphics() {
        this.setVisible(true);
        this.setSize(
        Settings.GRID_WIDTH * Settings.GRID_UNIT_SIZE + differenceWidth,
        Settings.GRID_HEIGHT * Settings.GRID_UNIT_SIZE + differenceHeight);
        this.setResizable(false);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initCamera() {
        camera = new Camera();
        setContentPane(camera);
        setVisible(true);
    }

    public void getDimensionDifferences() {
        differenceWidth = getSize().width - camera.getSize().width;
        differenceHeight = getSize().height - camera.getSize().height;
    }

    public void addEventHandler() {
        this.addKeyListener(Game.getController());
    }
}
