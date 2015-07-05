package model.item;

import java.awt.Point;

import model.CellObject;

/**
 * @author Eric Armbruster
 * @version 02.07.2015
 */
public abstract class Item extends CellObject {
	private final int chance;

	/**
	 * Subclasses can use this constructor to create a new Item.
	 * 
	 * @param image
	 *            the image of the Item
	 * @param position
	 *            the position of the Item
	 * @param chance
	 *            spawning-chance
	 */
	public Item(String image, Point position, int chance) {
		super(image, position);
		this.chance = chance;
	}

	/**
	 * @return spawning-chance
	 */
	public int getChance() {
		return chance;
	}
}