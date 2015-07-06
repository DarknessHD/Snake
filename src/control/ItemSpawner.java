package control;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import model.item.Apple;
import model.item.Booze;
import model.item.Item;
import model.item.Poop;
import model.item.RottenApple;

/**
 * @author Eric Armbruster, Stefan Kameter
 * @version 02.07.2015
 */
public class ItemSpawner {
	private static final Item[] items;

	static {
		items = new Item[4];
		items[0] = new Poop(null);
		items[1] = new Apple(null);
		items[2] = new RottenApple(null);
		items[3] = new Booze(null);

		Comparator<Item> comp = new Comparator<Item>() {
			@Override
			public int compare(Item i0, Item i1) {
				if (i0.getChance() < i1.getChance())
					return -1;
				else if (i0.getChance() > i1.getChance())
					return 1;
				return 0;
			}
		};
		Arrays.sort(items, comp);
	}

	/**
	 * Returns a random item at a random position.
	 * 
	 * @return a random item
	 */
	public static Item getRandomItem() {
		Item item = (Item) (items[getIndex()]).clone();

		Random random = new Random();
		while (true) {
			int x = random.nextInt(Constants.LEVEL_WIDTH);
			int y = random.nextInt(Constants.LEVEL_HEIGHT);

			if (item.setPosition(new Point(x, y)))
				break;
		}

		return item;
	}

	private static int getIndex() {
		int number = 0;
		for (Item item : items)
			number += item.getChance();

		int random = (int) (Math.random() * number);

		int temp = 0;
		for (int i = 0; i < items.length; i++) {
			int chance = items[i].getChance();
			if (chance + temp > random)
				return i;
			temp += chance;
		}

		return -1;
	}
}