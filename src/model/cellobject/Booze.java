package model.cellobject;

import java.awt.Point;

import model.Item;
import model.Snake;

/**
 * @author Alexander Donocik
 * @version 03.07.2015
 */
public class Booze extends Item {

	/**
	 * Creates an instance of Booze.
	 * 
	 * @param position
	 *            the position in the map
	 */
	public Booze(Point position) {
		super("booze", position);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.increaseScore(250);
	}
}