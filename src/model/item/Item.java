package model.item;

import model.CellObject;
import model.TilePosition;

/**
 * @author Eric Armbruster
 * @version 02.07.2015
 */
public abstract class Item extends CellObject {
	
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
	public Item(String image, TilePosition position, int chance, int usefulness) {
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
}