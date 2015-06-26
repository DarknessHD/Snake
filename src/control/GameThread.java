package control;

import input.KeyBoard;

import java.awt.event.KeyEvent;

import model.Direction;
import model.Snake;
import view.GameFrame;

/**
 * @author Stefan Kameter
 * @version 22.06.2015
 */
public class GameThread implements Runnable {
	private double ns;
	private int speed, sec;
	private boolean running;

	private Snake player;
	private Direction dir;

	/**
	 * Creates a GameThread instance.
	 */
	public GameThread() {
		speed = 5;
		ns = 1000000000.0 / speed;
		running = false;
	}

	/**
	 * Starts the thread.
	 */
	public void start() {
		running = true;
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Stops the thread.
	 */
	public void stop() {
		running = false;
	}

	/**
	 * Increases the game-speed.
	 * 
	 * @param increaseBy
	 *            the value that the speed gets increased by
	 */
	public void increaseSpeed(int increaseBy) {
		this.speed += increaseBy;
		ns = 1000000000.0 / this.speed;
	}

	/**
	 * Decreases the game-speed.
	 * 
	 * @param decreaseBy
	 *            the value that the speed gets decreased by
	 */
	public void decreaseSpeed(int decreaseBy) {
		this.speed -= decreaseBy;
		ns = 1000000000.0 / this.speed;
	}

	private void step() {
		if (player == null)
			player = GameFrame.getInstance().getSnake();
		// TODO InputCheck (Change MoveDirection, ...)
		player.setLookingDirection(dir);
		player.move();
		// TODO MoveSnake (Check: onItem, ...)
		// TODO Win / Loose
		GameFrame.getInstance().repaintGameCanvas();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				step();
				delta--;
			}

			GameFrame.getInstance().requestFocus();
			if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_W))
				dir = Direction.UP;
			if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_D))
				dir = Direction.RIGHT;
			if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_S))
				dir = Direction.DOWN;
			if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_A))
				dir = Direction.LEFT;

			if (System.currentTimeMillis() - timer > 1000) {
				GameFrame.getInstance().requestFocus();

				timer += 1000;
				int[] dirs = new int[4];
				sec++; // Do we really need sec?
				// TODO add Score
			}
		}
	}
}