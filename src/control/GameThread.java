package control;

import java.awt.event.KeyEvent;

import input.KeyBoard;
import model.Direction;
import model.Snake;
import view.GameFrame;
import view.GamePanel;
import control.snakecontroller.AIController;
import control.snakecontroller.PlayerController;
import control.snakecontroller.SnakeController;

/**
 * @author Stefan Kameter
 * @version 02.07.2015
 */
public class GameThread implements Runnable {
	private static final int MIN_SPEED = 2, MAX_SPEED = 10;

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
		if (speed < MIN_SPEED)
			speed = MIN_SPEED;
		else if (speed > MAX_SPEED)
			speed = MAX_SPEED;

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
			if (dirs[s] != null)
				snakes[s].setLookingDirection(dirs[s]);

			if (!snakes[s].move()) {
				stop();
				return;
			}
			gamePanel.onMove(s);

			// TODO painting
			// Point hp = snakes[s].getHead().getPosition();
			// System.out.println(hp.x << GamePanel.TILE_SIZE_BW);
			// gamePanel.repaint(new Rectangle(hp.x << GamePanel.TILE_SIZE_BW + 5, hp.y << GamePanel.TILE_SIZE_BW + 5, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE));
			// Point tp = snakes[s].getTail().getPosition();
			// gamePanel.repaint(new Rectangle(tp.x << GamePanel.TILE_SIZE_BW + 5, tp.y << GamePanel.TILE_SIZE_BW + 5, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE));
			// Direction opposite = snakes[s].getLookingDirection().getOpposite();
			// Point lp = new Point();
			// lp.setLocation(tp.x + opposite.getXOffset(), tp.y + opposite.getYOffset());
			// gamePanel.repaint(new Rectangle(lp.x << GamePanel.TILE_SIZE_BW + 5, lp.y << GamePanel.TILE_SIZE_BW + 5, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE));
			gamePanel.repaint();
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
		gamePanel.repaint();
	}
}