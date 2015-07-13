package control;

import java.util.Random;

import model.TilePosition;
import model.item.Apple;
import model.item.Banana;
import model.item.Booze;
import model.item.Crown;
import model.item.Item;
import model.item.Melon;
import model.item.Poop;
import model.item.RottenApple;
import view.GameFrame;

/**
 * @author Eric Armbruster, Stefan Kameter
 * @version 08.07.2015
 */
public class ItemSpawner {
	private static final Item[] items;

	static {
		items = new Item[7];
		items[0] = new Poop(null);
		items[1] = new Apple(null);
		items[2] = new RottenApple(null);
		items[3] = new Booze(null);
		items[4] = new Melon(null);
		items[5] = new Banana(null);
		items[6] = new Crown(null);
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

			TilePosition randomPos = new TilePosition(x, y);
			
			if (GameFrame.getInstance().getGamePanel().checkPosition(randomPos)) {
				item.setPosition(randomPos);
				break;
			}
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