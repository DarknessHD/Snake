package model;

import java.util.Objects;

import view.GameFrame;

/**
 * @author Eric Armbruster
 * @version 23.06.2015
 */
public class SnakeSegment extends CellObject {

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
	public SnakeSegment(String image, TilePosition position, Direction direction) {
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
	public SnakeSegment(String image, TilePosition startPosition, Direction direction, boolean opposite) {
		//Adjacent behaviour was changed!
		this(image, startPosition.getAdjacent((opposite) ? direction.getOpposite() : direction), direction);
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
}