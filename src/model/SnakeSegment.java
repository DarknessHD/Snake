package model;

import java.awt.Point;
import java.util.Objects;

import model.cellobject.CellObject;

/**
 * @author Eric Armbruster
 * @version 23.06.2015
 */
public class SnakeSegment extends CellObject {

	private Direction direction;
	
	/**
	 * Creates a new SnakeSegment instance.
	 * 
	 * @param image the image of the snake segment
	 * @param position the position of the snake segment
	 * @param direction the direction of the snake segment
	 */
	public SnakeSegment(String image, Point position, Direction direction) {
		super(image, position);
		this.direction = Objects.requireNonNull(direction);
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
	 * @param direction the direction of the snake segment
	 */
	public void setDirection(Direction direction) {
		this.direction = Objects.requireNonNull(direction);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		//TODO Game Over Screen!
	}
}