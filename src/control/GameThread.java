package control;

import input.KeyBoard;

import java.awt.Point;
import java.awt.event.KeyEvent;

import model.Direction;
import model.Snake;
import view.GameFrame;
import view.GamePanel;
import control.snakecontroller.AIController;
import control.snakecontroller.PlayerController;
import control.snakecontroller.SnakeController;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class GameThread implements Runnable {

	private static final SnakeController[] controllers;

	static {
		controllers = new SnakeController[2];
		controllers[0] = new PlayerController();
		controllers[1] = new AIController();
	}

	private GamePanel gamePanel;

	private int defaultSpeed;

	private double ns;
	private int speed;
	private boolean running;

	private Snake[] snakes;
	private Direction[] dirs;

	/**
	 * Creates a GameThread instance.
	 */
	public GameThread() {
		running = false;
	}

	/**
	 * Starts the thread.
	 * 
	 * @param defaultSpeed
	 *            the default speed
	 */
	public void start(int defaultSpeed) {
		if (gamePanel == null)
			gamePanel = GameFrame.getInstance().getGamePanel();
		this.defaultSpeed = defaultSpeed;
		setSpeedToDefaultSpeed();
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
		if (speed < Constants.MIN_SPEED)
			speed = Constants.MIN_SPEED;
		else if (speed > Constants.MAX_SPEED)
			speed = Constants.MAX_SPEED;

		ns = 1000000000.0 / speed;
	}

	/**
	 * Sets the game-speed to default.
	 */
	public void setSpeedToDefaultSpeed() {
		speed = defaultSpeed;
		ns = 1000000000.0 / speed;
	}

	private void step() {
		for (int s = 0; s < snakes.length; s++) {
			Point lastHeadPosition = (Point) snakes[s].getHead().getPosition().clone();
			Point lastTailPosition = (Point) snakes[s].getTail().getPosition().clone();

			if (dirs[s] != null)
				snakes[s].setLookingDirection(dirs[s]);

			if (!snakes[s].move()) {
				stop();
				return;
			}
			gamePanel.onMove(s);

			gamePanel.doRepaint(snakes[s].getHead().getPosition());
			gamePanel.doRepaint(snakes[s].getTail().getPosition());
			gamePanel.doRepaint(lastHeadPosition);
			gamePanel.doRepaint(lastTailPosition);
		}
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

			boolean paused = gamePanel.isPaused();

			while (delta >= 1) {
				if (!paused)
					step();
				delta--;
			}

			GameFrame.getInstance().requestFocus();

			if (!paused) {
				if (KeyBoard.getInstance().isKeyPressed(KeyEvent.VK_ESCAPE))
					gamePanel.setPaused(true);

				if (snakes == null) {
					snakes = gamePanel.getLevel().snakes;
					dirs = new Direction[snakes.length];
				}

				for (int s = 0; s < snakes.length; s++) {
					Direction dir = null;

					if (snakes[s].getPathfinder() == null)
						dir = controllers[0].getDirection(s);
					else
						dir = controllers[1].getDirection(s);

					if (dir != null)
						dirs[s] = dir;
				}
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				for (Snake s : snakes)
					s.increaseScore(speed * 2);
			}

			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		snakes = null;
		dirs = null;

		gamePanel.setGameOver(true);
	}
}