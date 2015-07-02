package model;

import java.awt.Point;

/**
 * @author Eric Armbruster
 * @version 02.07.2015
 */
public abstract class Item extends CellObject {
	/**
	 * Subclasses can use this constructor to create a new Item.
	 * 
	 * @param image
	 *            the image of the Item
	 * @param position
	 *            the position of the Item
	 */
	public Item(String image, Point position) {
		super(image, position);
	}
}