package model.cellobject;

import java.awt.Point;

import model.Item;
import model.Snake;

/**
 * @author Alexander Donocik
 * @version 03.07.2015
 */
public class Poop extends Item {

	/**
	 * Creates an instance of Poop.
	 * 
	 * @param position
	 *            the position in the map
	 */
	public Poop(Point position) {
		super("poop", position);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.increaseScore(1);
	}
}