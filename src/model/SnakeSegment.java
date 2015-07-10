package model;

import java.awt.Point;
import java.util.Objects;

import view.GameFrame;
import control.Constants;

/**
 * @author Eric Armbruster
 * @version 23.06.2015
 */
public class SnakeSegment extends CellObject {

	private static int count = 0;
	private Direction direction;

	/**
	 * Creates a new SnakeSegment instance.
	 * 
	 * @param image
	 *            the image of the snake segment
	 * @param position
	 *            the position of the snake segment
	 * @param direction
	 *            the direction of the snake segment
	 */
	public SnakeSegment(String image, Point position, Direction direction) {
		super(image, position);
		this.direction = Objects.requireNonNull(direction);
	}

	/**
	 * Creates a new SnakeSegment instance.
	 * 
	 * @param image
	 *            the image of the snake segment
	 * @param startPosition sP
	 * @param direction
	 *            the direction of the snake segment
	 * @param opposite
	 *            whether or not the opposite direction is used for getting the adjacent position
	 * @param endlessLevel
	 *            whether or not the level is endless
	 */
	public SnakeSegment(String image, Point startPosition, Direction direction, boolean opposite, boolean endlessLevel) {
		this(image, getAdjacentPosition(startPosition, (opposite) ? direction.getOpposite() : direction, endlessLevel), direction);
	}

	/**
	 * Returns the direction of the snake segment.
	 * 
	 * @return the direction of the snake segment
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Sets the direction of the snake segment.
	 * 
	 * @param direction
	 *            the direction of the snake segment
	 */
	public void setDirection(Direction direction) {
		this.direction = Objects.requireNonNull(direction);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		GameFrame.getInstance().stop();
	}

	private static Point getAdjacentPosition(Point startPosition, Direction direction, boolean endlessLevel) {
		Point copyStartPosition = new Point(startPosition);
		startPosition.setLocation((int) startPosition.getX() + direction.getXOffset(), (int) startPosition.getY() + direction.getYOffset());

		Point adjacent = new Point(startPosition);
		count++;

		if (endlessLevel) {
			if (adjacent.getX() < -1)
				adjacent.setLocation(Constants.LEVEL_WIDTH - 1, adjacent.getY());
			else if (adjacent.getY() < -1)
				adjacent.setLocation(adjacent.getX(), Constants.LEVEL_HEIGHT - 1);
			else if (adjacent.getX() > Constants.LEVEL_WIDTH - 1)
				adjacent.setLocation(0, adjacent.getY());
			else if (adjacent.getY() > Constants.LEVEL_HEIGHT - 1)
				adjacent.setLocation(adjacent.getX(), 0);
		} else if (adjacent.getX() > Constants.LEVEL_WIDTH - 1 || adjacent.getY() > Constants.LEVEL_HEIGHT - 1 || adjacent.getX() < 0 || adjacent.getY() < 0) {
			if (count < Direction.values().length)
				getAdjacentPosition(copyStartPosition, direction.getNext(), endlessLevel);
			else
				count = 0;
		}

		return adjacent;
	}
}