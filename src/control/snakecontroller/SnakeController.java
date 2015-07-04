package control.snakecontroller;

import model.Direction;

/**
 * @author Stefan Kameter
 * @version 02.07.2015
 */
public interface SnakeController {
	/**
	 * @param index
	 *            index of snake
	 * @return new Direction
	 */
	public Direction getDirection(int index);
}