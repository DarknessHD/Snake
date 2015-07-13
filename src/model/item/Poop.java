package model.item;

import model.Snake;
import model.TilePosition;
import view.GameFrame;

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
	public Poop(TilePosition position) {
		super("poop", position, 3, 1);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.increaseScore(5);
		GameFrame.getInstance().changeSpeed(-1);
	}
}