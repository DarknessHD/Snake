package model.item;

import java.awt.Point;

import view.GameFrame;
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
		super("booze", position, 2);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.increaseScore(150);
		GameFrame.getInstance().changeSpeed(1);
	}
}