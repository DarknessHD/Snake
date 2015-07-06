package model.item;

import java.awt.Point;

import model.Snake;

/**
 * @author Alexander Donocik
 * @version 29.06.2015
 */
public class Apple extends Item {

	/**
	 * Creates an instance of Apple.
	 * 
	 * @param position
	 *            the position in the map
	 */
	public Apple(Point position) {
		super("apple", position, 10, 3);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.addSegment();
		snake.increaseScore(75);
	}
}