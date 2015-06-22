package de.schoolproject.snake;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Eric
 * 22.06.2015
 */
public abstract class SnakeSegment extends CellObject {
	
	protected final Snake snake;
	
	/**
	 * Subclasses can use this constructor to create a new CellObject.
	 * 
	 * @param image - the image of the SnakeSegment
	 * @param position - the position of the SnakeSegment
	 * @param snake - the snake of the SnakeSegment
	 */
	public SnakeSegment(BufferedImage image, Point position, Snake snake) {
		super(image, position);
		this.snake = snake;
	}

	/**
	 * Returns the snake.
	 * 
	 * @return - the snake of the SnakeSegment
	 */
	public Snake getSnake() {
		return this.snake;
	}
}