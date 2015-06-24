package model;

/**
 * @author Eric Armbruster 22.06.2015
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
	RIGHT(0, -1);

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
		Direction direction = this;
		switch (direction) {
		case DOWN:
			direction = Direction.UP;
			break;
		case LEFT:
			direction = Direction.RIGHT;
			break;
		case RIGHT:
			direction = Direction.LEFT;
			break;
		case UP:
			direction = Direction.DOWN;
			break;
		}
		return direction;
	}
}