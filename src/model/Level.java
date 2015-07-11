package model;

import java.util.ArrayList;
import java.util.List;

import model.item.Item;
import model.item.RottenApple;
import view.GameFrame;
import control.Comp;
import control.Constants;
import control.ItemSpawner;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class Level {
	/**
	 * The level-name.
	 */
	public final String name;
	private final int width, height;
	private final boolean endless;
	private final int defaultSpeed;

	private final int itemNumber;

	/**
	 * The snakes.
	 */
	public final Snake[] snakes;
	/**
	 * The StaticCellObjects.
	 */
	public final StaticCellObject[] staticCellObjects;
	private List<Item> items;

	/**
	 * Creates an instance of Level.
	 * 
	 * @param name
	 *            the level-name
	 * @param width
	 *            the level-tile-width
	 * @param height
	 *            the level-tile-height
	 * @param endless
	 *            whether map is endless
	 * @param defaultSpeed
	 *            the defaultSpeed
	 * @param itemNumber
	 *            number of items
	 * @param snakes
	 *            the snakes
	 * @param staticCellObjects
	 *            the staticCellObjects
	 */
	public Level(String name, int width, int height, boolean endless, int defaultSpeed, int itemNumber, Snake[] snakes, StaticCellObject[] staticCellObjects) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.endless = endless;
		this.defaultSpeed = defaultSpeed;
		this.itemNumber = itemNumber;
		this.snakes = snakes;
		this.staticCellObjects = staticCellObjects;
		this.items = new ArrayList<Item>();
	}

	/**
	 * Initializes the Level.
	 * 
	 * @return whether Level can be used
	 */
	public boolean init() {
		if (!isAllowed())
			return false;

		Snake.setEndless(endless);

		int rottenApples = 0;
		Item item = null;
		for (int i = 0; i < itemNumber; i++) {
			item = ItemSpawner.getRandomItem();
			items.add(item);
			if (item instanceof RottenApple)
				rottenApples++;
		}
		if (rottenApples == itemNumber) {
			items.remove(item);
			do {
				item = ItemSpawner.getRandomItem();

			} while (item instanceof RottenApple);
			items.add(item);
		}

		GameFrame.getInstance().changeComponent(Comp.GAMEPANEL);
		GameFrame.getInstance().getGamePanel().repaint();
		GameFrame.getInstance().start(defaultSpeed);

		return true;
	}

	/**
	 * Returns whether the Level is allowed to be play.
	 * 
	 * @return whether the Level is allowed
	 */
	public boolean isAllowed() {
		if (Constants.LEVEL_WIDTH != width || Constants.LEVEL_HEIGHT != height || Constants.SPEED_MIN > defaultSpeed || Constants.SPEED_MAX < defaultSpeed)
			return false;
		return true;
	}

	/**
	 * Returns the items.
	 * 
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * Adds an Item.
	 * 
	 * @param item
	 *            the Item
	 */
	public void addItem(Item item) {
		items.add(item);

		GameFrame.getInstance().getGamePanel().doRepaint(item.getPosition());
	}

	@Override
	public Level clone() {
		Snake[] snakes = new Snake[this.snakes.length];
		for (int i = 0; i < snakes.length; i++)
			snakes[i] = (Snake) this.snakes[i].clone();

		StaticCellObject[] staticCellObjects = new StaticCellObject[this.staticCellObjects.length];
		for (int i = 0; i < staticCellObjects.length; i++)
			staticCellObjects[i] = (StaticCellObject) this.staticCellObjects[i].clone();

		return new Level(name, this.width, this.height, this.endless, this.defaultSpeed, this.itemNumber, snakes, staticCellObjects);
	}
}