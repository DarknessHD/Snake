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
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		// TODO Auto-generated method stub

	}
}