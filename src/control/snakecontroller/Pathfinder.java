package control.snakecontroller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import model.Direction;
import model.Snake;
import model.item.Item;
import view.GameFrame;

/**
 * @author Eric Armbruster, Stefan Kameter
 * @version 04.07.2015
 */
public class Pathfinder {

	/*
	 * TODO - intelligent Item-searching - Are there any objects on the Path? (dont bite yourself, dont run into walls)
	 */

	private Item target;
	private Snake snake;
	private List<Direction> path;

	/**
	 * Creates a new Pathfinder instance.
	 * 
	 * @param snake
	 *            that searches a path
	 */
	public Pathfinder(Snake snake) {
		this.snake = snake;
	}

	/**
	 * Returns the next Direction and removes it from the path.
	 * 
	 * @return the next Direction
	 */
	public Direction getNextDirection() {
		if (path == null || path.isEmpty()) {
			findPath();
			return getNextDirection();
		}
		Direction next = path.get(0);
		if (next == null) {
			findPath();
			return getNextDirection();
		}
		path.remove(0);

		return next;
	}

	/**
	 * Gets the Snake's path.
	 * 
	 * @return a list of directions
	 */
	public List<Direction> getPath() {
		return path;
	}

	/**
	 * Finds a new path to the target Item and sets it.
	 */
	public void findPath() {
		if (target == null)
			findNearestItem();

		Point currentPosition = (Point) snake.getHead().getPosition().clone();
		Point targetPosition = target.getPosition();
		List<Direction> path = new ArrayList<Direction>();

		Direction targetDirectionHorizontal;
		Direction targetDirectionVertical;
		int xDistance;
		int yDistance;

		do {

			// Find the target directions
			targetDirectionHorizontal = null;
			targetDirectionVertical = null;

			xDistance = (int) (currentPosition.getX() - targetPosition.getX());
			yDistance = (int) (currentPosition.getY() - targetPosition.getY());

			if (xDistance > 0)
				targetDirectionHorizontal = Direction.LEFT;
			else if (xDistance < 0)
				targetDirectionHorizontal = Direction.RIGHT;

			if (yDistance > 0)
				targetDirectionVertical = Direction.UP;
			else if (yDistance < 0)
				targetDirectionVertical = Direction.DOWN;

			// Get the resulting target direction
			Direction targetDirection;
			if (targetDirectionHorizontal == null)
				targetDirection = targetDirectionVertical;
			else if (targetDirectionVertical == null)
				targetDirection = targetDirectionHorizontal;
			else {
				if (new Random().nextBoolean())
					targetDirection = (targetDirectionHorizontal.getOpposite() == snake.getLookingDirection()) ? targetDirectionVertical : targetDirectionHorizontal;
				else
					targetDirection = (targetDirectionHorizontal.getOpposite() == snake.getLookingDirection()) ? targetDirectionVertical : targetDirectionHorizontal;
			}
			if (targetDirection == null) {
				findNearestItem();
				return;
			}

			// Get a new Point and get the next Position
			Point nextPosition = (Point) currentPosition.clone();

			nextPosition.setLocation(nextPosition.getX() + targetDirection.getXOffset(), nextPosition.getY() + targetDirection.getYOffset());
			path.add(targetDirection);

			currentPosition = nextPosition;
		} while (!currentPosition.equals(targetPosition));

		this.path = path;
	}

	/**
	 * Finds the nearest Items from the current position of the snake's head and sets it.
	 * 
	 * @return the nearest item
	 */
	public List<Item> findNearestItems() {
		List<Item> nearestItems = new ArrayList<Item>();

		Item nearestItem = null;
		Point headPosition = snake.getHead().getPosition();

		for (Item item : GameFrame.getInstance().getGamePanel().getLevel().items) {
			Point position = item.getPosition();
			if (nearestItem == null || position.distance(headPosition) < nearestItem.getPosition().distance(headPosition))
				nearestItem = item;
		}

		return nearestItem;
	}

	/**
	 * Finds the most useful Items from the current position of the snake's head and sets it.
	 * 
	 * @return the nearest item
	 */
	public List<Item> findMostUsefulItems() {
		List<Item> mostUsefulItems = new ArrayList<Item>();

		Item nearestItem = null;
		Point headPosition = snake.getHead().getPosition();
		List<Item> items = GameFrame.getInstance().getGamePanel().getLevel().items;
		Collections.sort(items);

		for (Item item : items) {
			Point position = item.getPosition();
			if (nearestItem == null || position.distance(headPosition) < nearestItem.getPosition().distance(headPosition))
				nearestItem = item;
		}

		return target = nearestItem;
	}

	/**
	 * Finds the most useful Item from the current position of the snake's head and sets it.
	 * 
	 * @return the nearest item
	 */
	public Item findBestItem() {
		Item nearestItem = null;
		Point headPosition = snake.getHead().getPosition();
		List<Item> items = GameFrame.getInstance().getGamePanel().getLevel().items;
		Collections.sort(items);

		for (Item item : items) {
			Point position = item.getPosition();
			if (nearestItem == null || position.distance(headPosition) < nearestItem.getPosition().distance(headPosition))
				nearestItem = item;
		}

		return target = nearestItem;
	}
}