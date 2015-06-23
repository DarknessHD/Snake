package model;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Eric Armbruster
 * @version 22.06.2015
 */
public abstract class CellObject {
	protected final BufferedImage image;
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
		this.position = position;
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
	 * 				the position
	 */
	public void setPosition(Point position) {
		this.position = position;
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
}