package control.snakecontroller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import view.GameFrame;
import model.Direction;
import model.Snake;
import model.item.Item;

/**
 * @author Eric Armbruster, Stefan Kameter
 * @version 04.07.2015
 */
public class Pathfinder {

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
		findPath();
	}
	
	/**
	 * Returns the next Direction and removes it from the path.
	 * 
	 * @return the next Direction
	 */
	public Direction getNextDirection() {
		Direction next = path.get(0);
		if(next == null) {
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

		this.path = path;
	}

	/**
	 * Finds the nearest Item from the current position of the snake's head and sets it.
	 * 
	 * @return the nearest item
	 */
	public Item findNearestItem() {
		Item nearestItem = null;
		Point headPosition = snake.getHead().getPosition();
		
		for (Item item : GameFrame.getInstance().getGamePanel().getLevel().items) {
			if(item != null) {
				Point position = item.getPosition();
				if (nearestItem == null || position.distance(headPosition) < nearestItem.getPosition().distance(headPosition))
					nearestItem = item;	
			}
		}
			

		return target = nearestItem;
	}
}