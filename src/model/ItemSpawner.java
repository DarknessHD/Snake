package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.cellobject.Apple;
import model.cellobject.RottenApple;
import view.GameCanvas;

/**
 * @author Eric Armbruster, Stefan Kameter
 * @version 02.07.2015
 */
public class ItemSpawner {

	private static final List<Item> items;

	static {
		items = new ArrayList<Item>();
		items.add(new Apple(null));
		items.add(new RottenApple(null));
	}

	/**
	 * Returns a random item at a random position.
	 * 
	 * @return a random item
	 */
	public static Item getRandomItem() {
		Item item = (Item) (items.get((int) (Math.random() * items.size()))).clone();

		while (true) {
			int x = (int) (Math.random() * GameCanvas.LEVEL_WIDTH);
			int y = (int) (Math.random() * GameCanvas.LEVEL_HEIGHT);

			if (item.setPosition(new Point(x, y)))
				break;
		}

		return item;
	}
}