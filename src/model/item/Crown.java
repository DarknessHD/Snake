package model.item;

import model.Snake;
import model.TilePosition;

/**
 * @author Eric Armbruster
 * @version 13.07.2015
 */
public class Crown extends Item {

	/**
	 * Creates an instance of Crown.
	 * 
	 * @param position
	 *            the position in the map
	 */
	public Crown(TilePosition position) {
		super("crown", position, 1, 6);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.increaseScore(500);
	}
}