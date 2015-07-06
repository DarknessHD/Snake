package control;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.SnakeSegment;
import model.item.Item;

/**
 * @author Eric Armbruster
 * @version 06.07.2015
 */
public class Items {

	private Items() {

	}

	/**
	 * Sorts the list of items by distance to SnakeSegment.
	 * 
	 * @param items
	 *            the list of items to sort
	 * @param distanceTo
	 *            the SnakeSegment that is used for the distance
	 * @return the sorted list of items
	 */
	public static List<? extends Item> sortByDistance(List<? extends Item> items, SnakeSegment distanceTo) {
		Point headPosition = distanceTo.getPosition();
		Collections.sort(items, (i0, i1) -> Double.compare(i0.getPosition().distance(headPosition), i1.getPosition().distance(headPosition)));
		
		return items;
	}

	/**
	 * Sorts the list of items by usefulness.
	 * 
	 * @param items
	 *            the list of items to sort
	 * @return the sorted list of items
	 */
	public static List<? extends Item> sortByUsefulness(List<? extends Item> items) {
		Collections.sort(items, (i0, i1) -> Integer.compare(i0.getUsefulness(), i1.getUsefulness()));

		return items;
	}

	/**
	 * Sorts the items array by chance.
	 * 
	 * @param items
	 *            the items array
	 * @return the sorted list of items
	 */
	public static Item[] sortByChance(Item[] items) {
		Arrays.sort(items, (i0, i1) -> Integer.compare(i0.getChance(), i1.getChance()));

		return items;
	}
}