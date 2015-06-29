package model;

import java.awt.Point;

public class Apple extends CellObject {

	public Apple(Point position) {
		super("apple", position);
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		snake.addSegment();
	}
}