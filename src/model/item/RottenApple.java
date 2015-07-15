package model.item;

import model.Snake;
import model.TilePosition;

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
	public RottenApple(TilePosition position) {
		super("apple_rotten", position, 12, 2);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		for (int i = 0; i < 2; i++)
			snake.removeSegment();
		snake.decreaseScore(25);
	}
}