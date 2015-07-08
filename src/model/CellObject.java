package model;

import java.awt.Point;
import java.awt.image.BufferedImage;

import view.GameFrame;

/**
 * @author Stefan Kameter, Eric Armbruster
 * @version 08.07.2015
 */
public abstract class CellObject implements Cloneable {

	protected String image;
	protected Point position;

	/**
	 * Subclasses can use this constructor to create a new CellObject.
	 * 
	 * @param image
	 *            the image of the CellObject
	 * @param position
	 *            the position of the CellObject
	 */
	public CellObject(String image, Point position) {
		this.image = image;
		this.position = position;
	}

	/**
	 * Returns the image of the CellObject.
	 * 
	 * @return the image
	 */
	public BufferedImage getImage() {
		return ImageHolder.getImage(image);
	}

	/**
	 * Sets the image of the CellObject.
	 * 
	 * @param image
	 *            the image
	 */
	public void setImage(String image) {
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
	 * @return true when the position was set, false otherwise
	 */
	public boolean setPosition(Point position) {
		if (GameFrame.getInstance().getGamePanel().checkPosition(position)) {
			this.position = position;
			return true;
		} else
			return false;
	}

	@Override
	public CellObject clone() {
		try {
			return (CellObject) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	/**
	 * Subclasses must implement their functionality, when they get hit by a snake.
	 * 
	 * @param snake
	 *            the snake, which has hit the CellObject
	 */
	public abstract void onSnakeHitCellObject(Snake snake);
}