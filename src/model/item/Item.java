package model.item;

import java.awt.Point;

import model.CellObject;

/**
 * @author Eric Armbruster
 * @version 02.07.2015
 */
public abstract class Item extends CellObject implements Comparable<Item> {
	private final int chance;
	private final int usefulness;

	/**
	 * Subclasses can use this constructor to create a new Item.
	 * 
	 * @param image
	 *            the image of the Item
	 * @param position
	 *            the position of the Item
	 * @param chance
	 *            spawning-chance
	 * @param usefulness
	 *            the usefulness (for AI)
	 */
	public Item(String image, Point position, int chance, int usefulness) {
		super(image, position);
		this.chance = chance;
		this.usefulness = usefulness;
	}

	/**
	 * @return spawning-chance
	 */
	public int getChance() {
		return chance;
	}

	/**
	 * @return usefulness
	 */
	public int getUsefulness() {
		return usefulness;
	}

	@Override
	public int compareTo(Item i) {
		if (usefulness > i.getUsefulness())
			return 1;
		else if (usefulness < i.getUsefulness())
			return -1;
		return 0;
	}
}