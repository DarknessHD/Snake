package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Stefan Kameter
 * @version 29.06.2015
 */
public class KeyBoard extends KeyAdapter {
	/**
	 * KeyCode for moving up.
	 */
	public static int UP = KeyEvent.VK_W;
	/**
	 * KeyCode for moving right.
	 */
	public static int RIGHT = KeyEvent.VK_D;
	/**
	 * KeyCode for moving down.
	 */
	public static int DOWN = KeyEvent.VK_S;
	/**
	 * KeyCode for moving left.
	 */
	public static int LEFT = KeyEvent.VK_A;

	private static KeyBoard instance;

	/**
	 * The Singleton.
	 * 
	 * @return the KeyBoard
	 */
	public static KeyBoard getInstance() {
		if (instance == null)
			instance = new KeyBoard();
		return instance;
	}

	private boolean[] keys;

	private KeyBoard() {
		keys = new boolean[256];
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		keys[ke.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		keys[ke.getKeyCode()] = false;
	}

	/**
	 * Whether the desired key is pressed.
	 * 
	 * @param keyCode
	 *            the keyCode of the desired Key.
	 * @return whether the desired key is pressed
	 */
	public boolean isKeyPressed(int keyCode) {
		return keys[keyCode];
	}
}