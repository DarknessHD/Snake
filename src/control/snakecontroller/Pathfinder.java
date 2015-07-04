package control.snakecontroller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Direction;
import model.Item;
import model.Snake;

/**
 * @author Eric Armbruster, Stefan Kameter
 * @version 04.07.2015
 */
public class Pathfinder {

	private Item target;
	private Snake snake;
	private Path path;

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
	 * Sets the next item target where the snake will go.
	 * 
	 * @param target
	 *            the target item
	 */
	public void setTarget(Item target) {
		this.target = target;
	}

	/**
	 * Returns the target item.
	 * 
	 * @return the target item
	 */
	public Item getTarget() {
		return target;
	}

	/**
	 * Sets the Snake's Path.
	 * 
	 * @param path
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Gets the Snake's Path.
	 * 
	 * @return the Snake's Path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Finds a new Path to the target Item.
	 * 
	 * @return a new Path instance
	 */
	public Path findPath() {
		if (target == null)
			return null;

		Point currentPosition = (Point) snake.getHead().getPosition().clone();
		Point targetPosition = target.getPosition();
		List<Direction> path = new ArrayList<Direction>();

		Direction targetDirectionHorizontal = null;
		Direction targetDirectionVertical = null;
		int xDistance;
		int yDistance;

		do {
			// TODO Are there any objects on the Path?

			// Find the target directions
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
			else
				targetDirection = (new Random().nextBoolean()) ? targetDirectionHorizontal : targetDirectionVertical;

			// Check if the snake can move into that direction
			if (targetDirection.getOpposite() == snake.getLookingDirection()) {
				// For now just take a random next directioon
				targetDirection = snake.getLookingDirection().getNext(new Random().nextBoolean());
			}

			// Get a new Point and get the next Position
			Point nextPosition = (Point) currentPosition.clone();

			nextPosition.setLocation(nextPosition.getX() + targetDirection.getXOffset(), nextPosition.getY()
					+ targetDirection.getYOffset());
			path.add(targetDirection);

			currentPosition = nextPosition;
		} while (!currentPosition.equals(targetPosition));

		return new Path(path);
	}

	/**
	 * Finds the nearest Item from the current position of the snake's head.
	 * 
	 * @param items
	 *            the map of items
	 * @return the nearest item
	 */
	public Item findNearestItem(List<Item> items) {
		Item nearestItem = null;
		Point headPosition = snake.getHead().getPosition();

		for (Item item : items) {
			Point position = item.getPosition();
			if (nearestItem == null || position.distance(headPosition) < nearestItem.getPosition().distance(headPosition))
				nearestItem = item;
		}
			

		return nearestItem;
	}

	//TODO Private
	public class Path {

		private final List<Direction> path;

		private Path(List<Direction> path) {
			this.path = path;
		}

		public List<Direction> getDirections() {
			return new ArrayList<Direction>(this.path);
		}
	}
}