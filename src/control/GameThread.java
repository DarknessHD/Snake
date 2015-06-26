package control;

import java.awt.event.KeyEvent;

import model.Direction;
import view.GameFrame;
import input.KeyBoard;

/**
 * @author Stefan Kameter
 * @version 22.06.2015
 */
public class GameThread implements Runnable {
	private double ns;
	private int speed, sec;
	private boolean running;

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
		// TODO InputCheck (Change MoveDirection, ...)
		if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_W)) 
			GameFrame.getInstance().getSnake().move(Direction.UP);
		if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_D))
			GameFrame.getInstance().getSnake().move(Direction.RIGHT);
		if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_S))
			GameFrame.getInstance().getSnake().move(Direction.DOWN);
		if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_A))
			GameFrame.getInstance().getSnake().move(Direction.LEFT);
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

			if (System.currentTimeMillis() - timer > 1000) {
				GameFrame.getInstance().requestFocus();

				timer += 1000;
				int[] dirs = new int[4];
				if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_W))
					dirs[0] = 1;
				if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_D))
					dirs[1] = 1;
				if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_S))
					dirs[2] = 1;
				if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_A))
					dirs[3] = 1;
				System.out.println("[ W: " + dirs[0] + ", A: " + dirs[3] + ", S: " + dirs[2] + ", D: " + dirs[1] + " ] (" + sec + "s)");
				sec++;
				// TODO add Score
			}
		}
	}
}