package model;

/**
 * @author Eric Armbruster
 * @version 22.06.2015
 */
public enum Direction {
	/**
	 * UP
	 */
	UP(0, -1),
	/**
	 * DOWN
	 */
	DOWN(0, 1),
	/**
	 * LEFT
	 */
	LEFT(-1, 0),
	/**
	 * RIGHT
	 */
	RIGHT(1, 0);

	private final int xOffset, yOffset;

	Direction(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	/**
	 * Returns the xOffset of the direction.
	 * 
	 * @return the xOffset of the direction
	 */
	public int getXOffset() {
		return xOffset;
	}

	/**
	 * Returns the yOffset of the direction.
	 * 
	 * @return the yOffset of the direction
	 */
	public int getYOffset() {
		return yOffset;
	}

	/**
	 * Returns the opposite direction.
	 * 
	 * @return the opposite direction
	 */
	public Direction getOpposite() {
		switch (this) {
		case DOWN:
			return Direction.UP;
		case LEFT:
			return Direction.RIGHT;
		case RIGHT:
			return Direction.LEFT;
		case UP:
			return Direction.DOWN;
		}
		return this;
	}
}