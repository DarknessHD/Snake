package model.item;

import java.awt.Point;

import view.GameFrame;
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
		super("poop", position, 2);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.increaseScore(5);
		GameFrame.getInstance().changeSpeed(-1);
	}
}