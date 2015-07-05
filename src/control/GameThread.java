package control;

import java.awt.Point;
import java.awt.Rectangle;

import model.Direction;
import model.Snake;
import view.GamePanel;
import view.GameFrame;
import control.snakecontroller.AIController;
import control.snakecontroller.PlayerController;
import control.snakecontroller.SnakeController;

/**
 * @author Stefan Kameter
 * @version 02.07.2015
 */
public class GameThread implements Runnable {
	private static final SnakeController[] controllers;

	static {
		controllers = new SnakeController[2];
		controllers[0] = new PlayerController();
		controllers[1] = new AIController();
	}

	private GamePanel gamePanel;

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
		if (gamePanel == null)
			gamePanel = GameFrame.getInstance().getGamePanel();
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
				return;
			}
			gamePanel.onMove(s);

			// painting
			Point p = snakes[s].getHead().getPosition();
			gamePanel.repaint(new Rectangle(p.x << GamePanel.TILE_SIZE_BW + 5, p.y << GamePanel.TILE_SIZE_BW + 5, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE));
			p = snakes[s].getTail().getPosition();
			gamePanel.repaint(new Rectangle(p.x << GamePanel.TILE_SIZE_BW + 5, p.y << GamePanel.TILE_SIZE_BW + 5, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE));
			Direction opposite = snakes[s].getLookingDirection().getOpposite();
			p = new Point();
			p.setLocation(p.x + opposite.getXOffset(), p.y + opposite.getYOffset());
			gamePanel.repaint(new Rectangle(p.x << GamePanel.TILE_SIZE_BW + 5, p.y << GamePanel.TILE_SIZE_BW + 5, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE));
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

			while (delta >= 1) {
				step();
				delta--;
			}

			GameFrame.getInstance().requestFocus();

			if (snakes == null) {
				snakes = gamePanel.getSnakes();
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

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// sec++;
				// TODO add Score per second to snakes
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