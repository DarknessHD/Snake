package de.schoolproject.snake;

import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class SnakeSegment extends CellObject {
	
	protected final Snake snake;
	
	public SnakeSegment(BufferedImage image, Point position, Snake snake) {
		super(image, position);
		this.snake = snake;
	}

	public Snake getSnake() {
		return this.snake;
	}
}