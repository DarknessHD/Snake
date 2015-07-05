package model.cellobject;

import java.awt.Point;

import view.GameFrame;
import model.CellObject;
import model.Snake;

/**
 * @author Alexander Donocik
 * @version 03.07.2015
 */
public class Wall extends CellObject {

	/**
	 * Creates an instance of Wall.
	 * 
	 * @param position
	 *            the position in the map
	 */
	public Wall(Point position) {
		super("wall", position);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		GameFrame.getInstance().stop();
	}
}