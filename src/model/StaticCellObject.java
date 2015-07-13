package model;

import view.GameFrame;

/**
 * @author Alexander Donocik
 * @version 10.07.2015
 */
public class StaticCellObject extends CellObject {

	/**
	 * Creates an instance of StaticCellObject.
	 * 
	 * @param position
	 *            the position of the CellObject
	 */
	public StaticCellObject(TilePosition position) {
		super("wall", position);
	}

	@Override
	public boolean setPosition(TilePosition position) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		GameFrame.getInstance().stop();
		if (GameFrame.getInstance().getGamePanel().getLevel().snakes.length != 1)
			snake.decreaseScore(350);
	}
}