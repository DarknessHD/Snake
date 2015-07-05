package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Stefan Kameter
 * @version 29.06.2015
 */
public class KeyBoard extends KeyAdapter {
	/**
	 * KeyCodes for moving up.
	 */
	public static final int[] UP = { KeyEvent.VK_W, KeyEvent.VK_UP };
	/**
	 * KeyCodes for moving right.
	 */
	public static final int[] RIGHT = { KeyEvent.VK_D, KeyEvent.VK_RIGHT };
	/**
	 * KeyCodes for moving down.
	 */
	public static final int[] DOWN = { KeyEvent.VK_S, KeyEvent.VK_DOWN };
	/**
	 * KeyCodes for moving left.
	 */
	public static final int[] LEFT = { KeyEvent.VK_A, KeyEvent.VK_LEFT };

	private static KeyBoard instance;

	/**
	 * The Singleton.
	 * 
	 * @return the KeyBoard instance
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
		if (ke.getKeyCode() < keys.length)
			keys[ke.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() < keys.length)
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