package control;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.SnakeSegment;
import model.item.Item;

/**
 * @author Eric Armbruster
 * @version 06.07.2015
 */
public class Items {
	/* #java7 */ private static Point headPosition;
	
	/* #java7 */ private static final Comparator<Item> distance = new Comparator<Item>() {
	/* #java7 */ 	public int compare(Item i1, Item i2) {
	/* #java7 */ 		if (i1.getPosition().distance(headPosition) < i2.getPosition().distance(headPosition))
	/* #java7 */ 			return -1;
	/* #java7 */ 		else if (i1.getPosition().distance(headPosition) > i2.getPosition().distance(headPosition))
	/* #java7 */ 			return 1;
	/* #java7 */ 		return 0;
	/* #java7 */	};
	/* #java7 */ };
	
	/* #java7 */ private static final Comparator<Item> usefulness = new Comparator<Item>() {
	/* #java7 */ 	public int compare(Item i1, Item i2) {
	/* #java7 */ 		if (i1.getUsefulness() < i2.getUsefulness())
	/* #java7 */ 			return -1;
	/* #java7 */ 		else if (i1.getUsefulness() > i2.getUsefulness())
	/* #java7 */ 			return 1;
	/* #java7 */ 		return 0;
	/* #java7 */	};
	/* #java7 */ };

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
	public static List<Item> sortByDistance(List<Item> items, SnakeSegment distanceTo) {
		headPosition = distanceTo.getPosition();
		/* #java8 */ // Collections.sort(items, (i0, i1) -> Double.compare(i0.getPosition().distance(headPosition), i1.getPosition().distance(headPosition))); // ORIGINAL
		/* #java7 */ Collections.sort(items, distance);

		return items;
	}

	/**
	 * Sorts the list of items by usefulness.
	 * 
	 * @param items
	 *            the list of items to sort
	 * @return the sorted list of items
	 */
	public static List<Item> sortByUsefulness(List<Item> items) {
		/* #java8 */ // Collections.sort(items, (i0, i1) -> Integer.compare(i0.getUsefulness(), i1.getUsefulness())); // ORIGINAL
		/* #java7 */ Collections.sort(items, usefulness);

		return items;
	}
}