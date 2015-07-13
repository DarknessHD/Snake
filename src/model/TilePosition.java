package model;

import java.awt.Point;

import view.GameFrame;
import control.Constants;

/**
 * @author Eric Armbruster
 * @version 13.07.2015
 */
public class TilePosition extends Point {
	
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	
	/**
	 * Creates a new TilePosition instance.
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
	public TilePosition(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Creates a new TilePosition instance.
	 * 
	 * @param position the TilePosition that is used to create the new tile position
	 */
	public TilePosition(TilePosition position) {
		super(position);
	}
	
	/**
	 * Gets the adjacent position.
	 * 
	 * @param direction the direction to use
	 * @return a new adjacent TilePosition in the direction
	 */
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
	
	/**
	 * Sets the position to the adjacent position in the direction.
	 * 
	 * @param direction
	 *            the direction
	 */
	public void setAdjacent(Direction direction) {
		this.setLocation(this.getAdjacent(direction));
	}
	
	/**
	 * Returns whether the position is within the game field.
	 * 
	 * @param position the position to check
	 * @return true, when the TilePosition is within the game field, false otherwise.
	 */
	public static boolean isWithinGameField(TilePosition position) {
		return !(position.getX() < 0 || position.getY() < 0 || position.getX() > Constants.LEVEL_WIDTH -1 || position.getY() > Constants.LEVEL_HEIGHT);
	}
}