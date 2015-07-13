package model.item;

import model.Snake;
import model.TilePosition;
import view.GameFrame;

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
	public Booze(TilePosition position) {
		super("booze", position, 4, 4);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.increaseScore(150);
		GameFrame.getInstance().changeSpeed(1);
	}
}