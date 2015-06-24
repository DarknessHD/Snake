package model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author Eric Armbruster, Stefan Kameter
 * @version 22.06.2015
 */
public abstract class CellObject {
	
	protected BufferedImage image;
	protected Point position;

	/**
	 * Subclasses can use this constructor to create a new CellObject.
	 * 
	 * @param image
	 *            the image of the CellObject
	 * @param position
	 *            the position of the CellObject
	 */
	public CellObject(BufferedImage image, Point position) {
		this.image = image;
		this.position = Objects.requireNonNull(position);
	}

	/**
	 * Returns the image of the CellObject.
	 * 
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Sets the image of the CellObject.
	 * 
	 * @param image
	 *            the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Returns the position of the CellObject.
	 * 
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Sets the position of the CellObject.
	 * 
	 * @param position
	 *            the position
	 */
	public void setPosition(Point position) {
		this.position = Objects.requireNonNull(position);
	}

	/**
	 * Moves the CellObject by xOffset and yOffset.
	 * 
	 * @param xOffset
	 *            the offset in x-axis
	 * @param yOffset
	 *            the offset in y-axis
	 */
	public void move(int xOffset, int yOffset) {
		this.position.move(xOffset, yOffset);
	}

	/**
	 * Subclasses must implement their functionality, when they get hit by a snake.
	 * 
	 * @param snake
	 *            the snake, which hit the CellObject
	 */
	public abstract void onSnakeHitCellObject(Snake snake);
}