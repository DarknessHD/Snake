package model.cellobject;

import java.awt.Point;

import model.Item;
import model.Snake;

/**
 * @author Alexander Donocik
 * @version 29.06.2015
 */
public class RottenApple extends Item {
	/**
	 * Creates an instance of RottenApple.
	 * 
	 * @param position
	 *            the position in the map
	 */
	public RottenApple(Point position) {
		super("apple", position);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		for (int i = 0; i < 2; i++)
			snake.removeSegment();
	}
}