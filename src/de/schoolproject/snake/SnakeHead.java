package de.schoolproject.snake;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class SnakeHead extends SnakeSegment {

	protected Direction direction;
	
	public SnakeHead(BufferedImage image, Point position, Snake snake, Direction direction) {
		super(image, position, snake);
		
		if(direction == null)
			this.direction = Direction.UP;
		else
			this.direction = direction;
	}
	
	public SnakeHead(BufferedImage image, Point position, Snake snake) {
		this(image, position, snake, null);
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
}