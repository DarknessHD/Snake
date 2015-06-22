package de.schoolproject.snake;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Eric
 * 22.06.2015
 */
public class SnakeHead extends SnakeSegment {

	protected Direction direction;
	
	/**
	 * Creates a new SnakeHead.
	 * 
	 * @param image - the image of the SnakeHead
	 * @param position - the position of the SnakeHead
	 * @param snake - the snake of the SnakeHead
	 * @param direction - the direction of the SnakeHead
	 */
	public SnakeHead(BufferedImage image, Point position, Snake snake, Direction direction) {
		super(image, position, snake);
		
		if(direction == null)
			this.direction = Direction.UP;
		else
			this.direction = direction;
	}
	
	/**
	 * Creates a new SnakeHead.
	 * 
	 * @param image - the image of the SnakeHead
	 * @param position - the position of the SnakeHead
	 * @param snake - the snake of the SnakeHead
	 */
	public SnakeHead(BufferedImage image, Point position, Snake snake) {
		this(image, position, snake, null);
	}
	
	/**
	 * Sets the direction.
	 * 
	 * @param direction - the direction of the SnakeHead
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * Returns the direction.
	 * 
	 * @return - the direction of the SnakeHead
	 */
	public Direction getDirection() {
		return this.direction;
	}
}
