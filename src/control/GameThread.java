package control;

import java.awt.event.KeyEvent;

import view.GameFrame;

import input.KeyBoard;

/**
 * @author Stefan
 * @version 22.06.2015
 */
public class GameThread implements Runnable {
	private double ns;
	private int speed;
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
	 * Changes the game-speed.
	 * 
	 * @param speed
	 *            the addition to the speed
	 */
	public void addSpeed(int speed) {
		this.speed += speed;
		ns = 1000000000.0 / this.speed;
	}

	private void step() {
		// TODO InputCheck (Change MoveDirection, ...)
		// TODO MoveSnake (Check: onItem, ...)
		// TODO Win / Loose
	}

	private int sec;

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