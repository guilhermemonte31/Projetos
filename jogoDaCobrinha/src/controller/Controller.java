package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/** This class handles the way the player controls the game */
public class Controller implements KeyListener {
	public enum ControlType {
		ARROWS,
		WASD
	}

	public enum KeyMap {
		LEFT(37, 65), UP(38, 87), RIGHT(39, 68), DOWN(40, 83),
		ENTER(10, 10);
//		W(87), A(65), S(83), D(68);
		public final int v1;
		public final int v2;
		KeyMap(int val1, int val2) {
			v1 = val1;
			v2 = val2;
		}
	}

	HashMap<Integer, Boolean> keyPressed = new HashMap<>();

	public Controller() {
		keyPressed.put(KeyMap.LEFT.v1, false);
		keyPressed.put(KeyMap.RIGHT.v1, false);
		keyPressed.put(KeyMap.UP.v1, false);
		keyPressed.put(KeyMap.DOWN.v1, false);
		keyPressed.put(KeyMap.LEFT.v2, false);
		keyPressed.put(KeyMap.RIGHT.v2, false);
		keyPressed.put(KeyMap.UP.v2, false);
		keyPressed.put(KeyMap.DOWN.v2, false);
	}

	public void setPressState(int key, boolean pressed) {
		if (keyPressed.containsKey(key))
			keyPressed.put(key, pressed);
	}
	
	public boolean getStatePressed(int key) {
		return keyPressed.get(key);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		setPressState(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		setPressState(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
