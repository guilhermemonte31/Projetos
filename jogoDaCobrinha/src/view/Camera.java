package view;

import model.Game;
import model.Physical;
import model.Snake;
import resources.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Camera extends JPanel {
    public void paintComponent(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        /* Draw the background */
        g2d.drawImage(Ground.imgTerrain, 0, 0, null);

        for (Display object : Display.displayableMap.values()) {
            object.display(g2d);
        }

        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        int scoreY = 2*Settings.GRID_UNIT_SIZE -3;
        for (Snake player: Game.players) {
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(player.getScore()), 35, scoreY);
            scoreY += Settings.GRID_UNIT_SIZE;

        }
    }
}
