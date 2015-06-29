package model;

import java.awt.Point;

public class RottenApple extends CellObject {

	public RottenApple(Point position) {
		super("apple_rotten", position);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		for (int i = 0; i < 2; i++)
			snake.removeSegment();
	}
}