package control.snakecontroller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Direction;
import model.Snake;
import model.item.Item;
import view.GameFrame;
import control.Items;

/**
 * @author Eric Armbruster, Stefan Kameter
 * @version 04.07.2015
 */
public class Pathfinder {

	/*
	 * TODO - intelligent Item-searching - Are there any objects on the Path?
	 * (dont bite yourself, dont run into walls)
	 * end path if item was removed!
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
			findTargetItem();

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
					targetDirection = (targetDirectionHorizontal.getOpposite() == snake.getLookingDirection()) ? targetDirectionVertical
							: targetDirectionHorizontal;
				else
					targetDirection = (targetDirectionHorizontal.getOpposite() == snake.getLookingDirection()) ? targetDirectionVertical
							: targetDirectionHorizontal;
			}
			if (targetDirection == null) {
				findTargetItem();
				return;
			}

			// Get a new Point and get the next Position
			Point nextPosition = (Point) currentPosition.clone();

			nextPosition.setLocation(nextPosition.getX() + targetDirection.getXOffset(), nextPosition.getY()
					+ targetDirection.getYOffset());
			path.add(targetDirection);

			currentPosition = nextPosition;
		} while (!currentPosition.equals(targetPosition));

		this.path = path;
	}

	/**
	 * Finds the most useful item and the one with the shortest distance to.
	 */
	public void findTargetItem() {
		List<Item> items = GameFrame.getInstance().getGamePanel().getLevel().getItems();
		Items.sortByUsefulness(items);

		int highestUsefulness = items.get(0).getUsefulness();
		int i = 0;
		for (; i < items.size(); i++) {
			if (i == 0)
				highestUsefulness = items.get(i).getUsefulness();
			else if (items.get(i).getUsefulness() < highestUsefulness)
				break;
		}

		List<? extends Item> bestItems = Items.sortByDistance(items.subList(0, i), snake.getHead());

		target = bestItems.get(0);
	}
}