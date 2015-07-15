package model.item;

import model.Snake;
import model.TilePosition;

/**
 * @author Eric Armbruster
 * @version 13.07.2015
 */
public class Banana extends Item {

	/**
	 * Creates an instance of Banana.
	 * 
	 * @param position
	 *            the position in the map
	 */
	public Banana(TilePosition position) {
		super("banana", position, 4, 4);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.addSegments(2);
		snake.increaseScore(130);
	}
}