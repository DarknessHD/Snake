package model.static_co;

import java.awt.Point;

import model.Snake;
import view.GameFrame;

/**
 * @author Alexander Donocik
 * @version 03.07.2015
 */
public class Wall extends StaticCellObject {

	/**
	 * Creates an instance of Wall.
	 * 
	 * @param position
	 *            the position in the map
	 */
	public Wall(Point position) {
		super(0, "wall", position);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		GameFrame.getInstance().stop();
		if (GameFrame.getInstance().getGamePanel().getLevel().snakes.length != 1)
			snake.decreaseScore(350);
	}

	@Override
	public int getId() {
		return 0;
	}
}