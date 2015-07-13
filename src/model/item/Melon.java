package model.item;

import model.Snake;
import model.TilePosition;

/**
 * @author Eric Armbruster
 * @version 13.07.2015
 */
public class Melon extends Item {

	/**
	 * Creates an instance of Melon.
	 * 
	 * @param position
	 *            the position in the map
	 */
	public Melon(TilePosition position) {
		super("melon", position, 1, 5);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		for(int i = 0; i < 5; i++)
			snake.addSegment();
		snake.increaseScore(300);
	}
}