package model;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Stefan
 * @version 22.06.2015
 */
public abstract class CellObject {
	protected final BufferedImage image;
	protected Point position;

	/**
	 * Subclasses can use this Constructor to create a new Drawable instance.
	 * 
	 * @param image
	 *            the image of the Drawable
	 * @param position
	 *            the position of the Drawable
	 */
	public CellObject(BufferedImage image, Point position) {
		this.image = image;
		this.position = position;
	}

	/**
	 * A getter-method for the image.
	 * 
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * A getter-method for the position.
	 * 
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Moves the Drawable.
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