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
		super("banana", position, 3, 4);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		for(int i = 0; i < 2; i++)
			snake.addSegment();
		snake.increaseScore(130);
	}
}