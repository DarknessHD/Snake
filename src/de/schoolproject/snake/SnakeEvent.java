package de.schoolproject.snake;

import java.util.EventObject;

/**
 * @author Eric
 * 22.06.15
 */
@SuppressWarnings("serial")
public class SnakeEvent extends EventObject {

	/**
	 * Creates a new SnakeEvent.
	 * 
	 * @param snake - the snake of the SnakeEvent
	 */
	public SnakeEvent(Snake snake) {
		super(snake);
	}
}