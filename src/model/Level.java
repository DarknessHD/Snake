package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.item.Item;
import model.staticobjects.StaticCellObject;
import model.staticobjects.Wall;
import view.GameFrame;
import control.Comp;
import control.Constants;
import control.ItemSpawner;

/**
 * @author Stefan Kameter
 * @version 05.07.2015
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
	/**
	 * The items.
	 */
	public List<Item> items;

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
		// this.snakes = snakes;
		// this.staticCellObjects = staticCellObjects;
		this.items = new ArrayList<Item>();

		// TODO
		this.items = new ArrayList<Item>();
		this.staticCellObjects = new StaticCellObject[(width - 2) * 2 + (height - 2) * 2];
		int id = 0;
		for (int i = 0; i < height; i++) {
			if (i > 5 && i < 8)
				continue;
			this.staticCellObjects[id] = new Wall(new Point(0, i));
			id++;
			this.staticCellObjects[id] = new Wall(new Point(width - 1, i));
			id++;
		}
		for (int i = 1; i < width - 1; i++) {
			this.staticCellObjects[id] = new Wall(new Point(i, 0));
			id++;
			this.staticCellObjects[id] = new Wall(new Point(i, height - 1));
			id++;
		}
		this.snakes = new Snake[1];
		this.snakes[0] = new Snake(3, new Point(4, 5), Direction.DOWN);
		//this.snakes[1] = new Snake(3, new Point(20, 5), Direction.DOWN);
		//this.snakes[1].setPathfinder();
		// TODO
	}

	/**
	 * Initializes the Level.
	 * 
	 * @return whether Level can be used
	 */
	public boolean init() {
		if (Constants.LEVEL_WIDTH != width || Constants.LEVEL_HEIGHT != height)
			return false;
		Snake.setEndless(endless);
		for (int i = 0; i < itemNumber; i++)
			addItem(ItemSpawner.getRandomItem());
		GameFrame.getInstance().start(defaultSpeed);
		GameFrame.getInstance().changeComponent(Comp.GAMEPANEL);
		return true;
	}

	/**
	 * Adds an Item.
	 * 
	 * @param item
	 *            the Item
	 */
	public void addItem(Item item) {
		items.add(item);
		// TODO if not repainting then delete this method
		// TODO repainting
		// Point p = item.getPosition();
		// repaint(new Rectangle(p.x << TILE_SIZE_BW + 5, p.y << TILE_SIZE_BW + 5, TILE_SIZE, TILE_SIZE));
	}
}
