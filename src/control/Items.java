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
	/* #java1.7 */private static Point headPosition;

	/* #java1.7 */private static final Comparator<Item> distance = new Comparator<Item>() {
		/* #java1.7 */public int compare(Item i1, Item i2) {
			/* #java1.7 */if (i1.getPosition().distance(headPosition) < i2.getPosition().distance(headPosition))
				/* #java1.7 */return -1;
			/* #java1.7 */else if (i1.getPosition().distance(headPosition) > i2.getPosition().distance(headPosition))
				/* #java1.7 */return 1;
			/* #java1.7 */return 0;
			/* #java1.7 */};
		/* #java1.7 */
	};

	/* #java1.7 */private static final Comparator<Item> usefulness = new Comparator<Item>() {
		/* #java1.7 */public int compare(Item i1, Item i2) {
			/* #java1.7 */if (i1.getUsefulness() < i2.getUsefulness())
				/* #java1.7 */return -1;
			/* #java1.7 */else if (i1.getUsefulness() > i2.getUsefulness())
				/* #java1.7 */return 1;
			/* #java1.7 */return 0;
			/* #java1.7 */};
		/* #java1.7 */
	};

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
		// /* #java1.8 */ Collections.sort(items, (i0, i1) -> Double.compare(i0.getPosition().distance(headPosition), i1.getPosition().distance(headPosition))); // ORIGINAL
		/* #java1.7 */Collections.sort(items, distance);

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
		// /* #java1.8 */ Collections.sort(items, (i0, i1) -> Integer.compare(i0.getUsefulness(), i1.getUsefulness())); // ORIGINAL
		/* #java1.7 */Collections.sort(items, usefulness);

		return items;
	}
}