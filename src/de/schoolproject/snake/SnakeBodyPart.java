package de.schoolproject.snake;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Eric
 * 22.06.2015
 */
public class SnakeBodyPart extends SnakeSegment {

	/**
	 * Creates a new SnakeBodyPart.
	 * 
	 * @param image - the image of the SnakeBodyPart
	 * @param position - the image of the SnakeBodyPart
	 * @param snake - the image of the SnakeBodyPart
	 */
	public SnakeBodyPart(BufferedImage image, Point position, Snake snake) {
		super(image, position, snake);
	}
	
}