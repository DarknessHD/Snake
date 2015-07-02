package control;

import input.KeyBoard;
import model.Direction;
import model.Snake;
import view.Comp;
import view.GameFrame;

/**
 * @author Stefan Kameter
 * @version 27.06.2015
 */
public class GameThread implements Runnable {
	private double ns;
	private int speed/* , sec */;
	private boolean running;

	private Snake[] snakes;
	private Direction[] dirs;

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
	 * @param speedAddition
	 *            the value that the speed gets in-/decreased by
	 */
	public void changeSpeed(int speedAddition) {
		speed += speedAddition;
		ns = 1000000000.0 / speed;
	}

	private void step() {
		for (int s = 0; s < snakes.length; s++) {
			if (dirs[s] != null)
				snakes[s].setLookingDirection(dirs[s]);

			if (!snakes[s].move()) {
				stop();
				GameFrame.getInstance().changeComponent(Comp.GAMEMENUPANEL);
				// TODO add score to ScoreList
				return;
			}
			GameFrame.getInstance().onMove(s);
		}
		GameFrame.getInstance().repaintGameCanvas();
	}

	@SuppressWarnings("static-access")
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

			if (snakes == null) {
				snakes = GameFrame.getInstance().getSnakes();
				dirs = new Direction[snakes.length];
			}

			for (int s = 0; s < snakes.length; s++) {
				if (KeyBoard.getInstance().isKeyPressed(KeyBoard.getInstance().UP[s]))
					dirs[s] = Direction.UP;
				if (KeyBoard.getInstance().isKeyPressed(KeyBoard.getInstance().RIGHT[s]))
					dirs[s] = Direction.RIGHT;
				if (KeyBoard.getInstance().isKeyPressed(KeyBoard.getInstance().DOWN[s]))
					dirs[s] = Direction.DOWN;
				if (KeyBoard.getInstance().isKeyPressed(KeyBoard.getInstance().LEFT[s]))
					dirs[s] = Direction.LEFT;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// sec++;
				// TODO add Score per second
			}
		}

		snakes = null;
		dirs = null;
	}
}