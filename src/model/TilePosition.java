package model;

import java.awt.Point;

import view.GameFrame;
import control.Constants;

public class TilePosition extends Point {
	
	private static int count = 0;
	
	public TilePosition(int x, int y) {
		super(x, y);
	}
	
	public TilePosition(TilePosition position) {
		super(position);
	}
	
	public TilePosition getAdjacent(Direction direction) {
		TilePosition adjacent = new TilePosition((int) this.getX() + direction.getXOffset(), (int) this.getY() + direction.getYOffset());

		if (GameFrame.getInstance().getGamePanel().getLevel().endless) {
			if (adjacent.getX() < 0)
				adjacent.setLocation(Constants.LEVEL_WIDTH - 1, adjacent.getY());
			else if (adjacent.getY() < 0)
				adjacent.setLocation(adjacent.getX(), Constants.LEVEL_HEIGHT - 1);
			else if (adjacent.getX() > Constants.LEVEL_WIDTH - 1)
				adjacent.setLocation(0, adjacent.getY());
			else if (adjacent.getY() > Constants.LEVEL_HEIGHT - 1)
				adjacent.setLocation(adjacent.getX(), 0);
			
		} else if (adjacent.getX() > Constants.LEVEL_WIDTH - 1 || adjacent.getY() > Constants.LEVEL_HEIGHT - 1 || adjacent.getX() < 0 || adjacent.getY() < 0) {
			//Does this make sense?
			count++;
			if (count < Direction.values().length)
				getAdjacent(direction.getNext());
			else
				count = 0;
		}

		return adjacent;
	}
	
	public static boolean isWithinGameField(TilePosition position) {
		return !(position.getX() < 0 || position.getY() < 0 || position.getX() > Constants.LEVEL_WIDTH -1 || position.getY() > Constants.LEVEL_HEIGHT);
	}
}