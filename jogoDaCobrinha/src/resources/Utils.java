package resources;

import java.awt.Color;

import resources.Settings.Direction;

public class Utils {
	public static int incrementValueByDirection(Direction d, Direction sens, int value, int delta) {
		if (d == sens || d == sens.opposite()) {
			return switch (d) {
				case LEFT, UP -> value - delta;
				case RIGHT, DOWN -> value + delta;
				default -> value;
			};
		}
		return value;
	}
	
	public static double incrementValueByDirection(Direction d, double value, int deltaX, int deltaY, int mx, int my) {
		return switch (d) {
			case LEFT -> value + mx * deltaX;
			case UP -> value + my * deltaY;
			case RIGHT -> value + deltaX;
			case DOWN -> value + deltaY;
			default -> value;
		};
	}
	
	public static Color getColorGradient(int length, int part, Color c1, Color c2) {
		int r1 = c1.getRed();
		int b1 = c1.getBlue();
		int v1 = c1.getGreen();
		int r2 = c2.getRed();
		int b2 = c2.getBlue();
		int v2 = c2.getGreen();
		int nr = (int)(r1 + ((double)part/(double)length)*(r2-r1));
		int nb = (int)(b1 + ((double)part/(double)length)*(b2-b1));
		int nv = (int)(v1 + ((double)part/(double)length)*(v2-v1));
		return new Color(nr,nv,nb);
	}
	
	public static boolean checkCollision(int x1, int y1, int x2, int y2) {
		int nx1 = x1 / Settings.GRID_UNIT_SIZE;
		int nx2 = x2 / Settings.GRID_UNIT_SIZE;
		int ny1 = y1 / Settings.GRID_UNIT_SIZE;
		int ny2 = y2 / Settings.GRID_UNIT_SIZE;

		return nx1 == nx2 && ny1 == ny2;
	}
	
	public static boolean checkCollision(int x1, int y1, int x2, int y2, int x3, int y3) {
		int nx1 = x1 / Settings.GRID_UNIT_SIZE;
		int ny1 = y1 / Settings.GRID_UNIT_SIZE;
		
		int nx2 = x2 / Settings.GRID_UNIT_SIZE;
		int ny2 = y2 / Settings.GRID_UNIT_SIZE;

		int nx3 = x3 / Settings.GRID_UNIT_SIZE;
		int ny3 = y3 / Settings.GRID_UNIT_SIZE;

		if (
			(nx1 == nx2 && (ny1 <= ny2 && ny3<=ny1)) ||
			(nx1 == nx2 && (ny1 >= ny2 && ny3>=ny1))) {
			return true;
		}
		
		return 
			(ny1 == ny2 && (nx1 <= nx2 && nx3 <= nx1)) || 
			(ny1 == ny2 && (nx1 >= nx2 && nx3 >= nx1));
	}
}
