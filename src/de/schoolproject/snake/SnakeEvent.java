package de.schoolproject.snake;

import java.util.EventObject;

@SuppressWarnings("serial")
public class SnakeEvent extends EventObject {

	public SnakeEvent(Snake snake) {
		super(snake);
	}
}