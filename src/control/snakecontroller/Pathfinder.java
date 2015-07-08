package control.snakecontroller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import model.CellObject;
import model.Direction;
import model.Level;
import model.Snake;
import model.item.Item;
import view.GameFrame;
import control.Items;

/**
 * @author Eric Armbruster
 * @version 04.07.2015
 */
public class Pathfinder {

	/*
	 *TODO  Fix walking into the wall
	 */

	private Item target;
	private Snake aiSnake;
	private Direction nextDirection;

	/**
	 * Creates a new Pathfinder instance.
	 * 
	 * @param aiSnake
	 *            that follows a path
	 */
	public Pathfinder(Snake aiSnake) {
		this.aiSnake = aiSnake;
	}

	/**
	 * Returns the next Direction and removes it from the path.
	 * 
	 * @return the next Direction
	 */
	public Direction getNextDirection() {
		if (nextDirection == null)
			findNextDirection();

		Direction next = nextDirection;
		nextDirection = null;
		return next;
	}

	/**
	 * Finds a next direction and sets it.
	 */
	public void findNextDirection() {
		Level level = GameFrame.getInstance().getGamePanel().getLevel();
		List<Item> items = level.getItems();

		findTargetItem();

		Point currentPosition = (Point) new Point(aiSnake.getHead().getPosition());
		Point targetPosition = target.getPosition();

		// Find the target directions
		Direction targetDirectionHorizontal = null;
		Direction targetDirectionVertical = null;
		int xDistance = (int) (currentPosition.getX() - targetPosition.getX());
		int yDistance = (int) (currentPosition.getY() - targetPosition.getY());

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
				targetDirection = (targetDirectionHorizontal.getOpposite() == aiSnake.getLookingDirection()) ? targetDirectionVertical
						: targetDirectionHorizontal;
			else
				targetDirection = (targetDirectionHorizontal.getOpposite() == aiSnake.getLookingDirection()) ? targetDirectionVertical
						: targetDirectionHorizontal;
		}

		// Get the next position
		currentPosition.setLocation(currentPosition.getX() + targetDirection.getXOffset(), currentPosition.getY()
				+ targetDirection.getYOffset());

		// Check if there are any CellObjects on the path, then move into
		// the next (last) direction
		List<CellObject> cellObjects = new ArrayList<>(items);
		cellObjects.addAll(Arrays.asList(level.staticCellObjects));
		cellObjects.addAll(4, aiSnake.getSegments());
		cellObjects.addAll(GameFrame.getInstance().getGamePanel().getLevel().snakes[0].getSegments());
		cellObjects.remove(target);
		
		for (CellObject cellObject : cellObjects) {
			if (cellObject.getPosition().equals(currentPosition)) {
				targetDirection = targetDirection.getNext(new Random().nextBoolean());
				//TODO Target direction checks
				//Recall this method to make sure the new target direction works
				break;
			}
		}

		this.nextDirection = targetDirection;
	}

	/**
	 * Finds the most useful item and the one with the shortest distance to.
	 */
	public void findTargetItem() {
		List<Item> items = Items.sortByUsefulness(GameFrame.getInstance().getGamePanel().getLevel().getItems());
		items.removeIf((Item item) -> item.getUsefulness() != items.get(items.size()-1).getUsefulness());

		target = Items.sortByDistance(items, aiSnake.getHead()).get(0);
	}
}
