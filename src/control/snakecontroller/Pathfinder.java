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
import model.SnakeSegment;
import model.StaticCellObject;
import model.item.Item;
import view.GameFrame;
import control.Items;

/**
 * @author Eric Armbruster
 * @version 04.07.2015
 */
public class Pathfinder {

	/*
	 * TODO Fix walking into the wall
	 */

	private final Snake aiSnake;
	private Item target;
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
			findNextDirection(null, null);

		Direction next = nextDirection;
		nextDirection = null;
		return next;
	}

	/**
	 * @return the ai snake
	 */
	public Snake getSnake() {
		return aiSnake;
	}

	/**
	 * @return the target item
	 */
	public Item getTargetItem() {
		return target;
	}

	private void findNextDirection(Direction targetDirectionHorizontal, Direction targetDirectionVertical) {
		Level level = GameFrame.getInstance().getGamePanel().getLevel();
		List<Item> items = level.getItems();

		findTargetItem();

		Point currentPosition = new Point(aiSnake.getHead().getPosition());
		Point targetPosition = target.getPosition();

		// Find the target directions
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
		Direction targetDirection = null;
		
		if(isSameAsOppositeOfLookingDirection(targetDirectionVertical) && targetDirectionHorizontal != null)
			targetDirection = targetDirectionHorizontal;
		else if(isSameAsOppositeOfLookingDirection(targetDirectionHorizontal) && targetDirectionVertical != null)
			targetDirection = targetDirectionVertical;
		
		if(targetDirection == null) {
			if (targetDirectionHorizontal == null)
				targetDirection = targetDirectionVertical;
			else if (targetDirectionVertical == null)
				targetDirection = targetDirectionHorizontal;
			else if (new Random().nextBoolean())
				targetDirection = targetDirectionHorizontal;
			else
				targetDirection = targetDirectionVertical;
		}

		// Get the next position
		currentPosition.setLocation(currentPosition.getX() + targetDirection.getXOffset(), currentPosition.getY()
				+ targetDirection.getYOffset());

		// Check if there are any CellObjects on the path, then move into
		// the next (last) direction
		List<CellObject> cellObjects = new ArrayList<CellObject>(items);
		cellObjects.addAll(Arrays.asList(level.staticCellObjects));
		cellObjects.addAll(4, aiSnake.getSegments());
		cellObjects.addAll(GameFrame.getInstance().getGamePanel().getLevel().snakes[0].getSegments());
		cellObjects.remove(target);

		for (CellObject cellObject : cellObjects) {
			if (cellObject.getPosition().equals(currentPosition)) {
				if(cellObject instanceof SnakeSegment || cellObject instanceof StaticCellObject) {
					Direction safeDirection = findSafeDirection(targetDirection);
					if(safeDirection != null)
						targetDirection = safeDirection;
					else {
						//NO SAFE DIRECTION! Snake will die!
						System.out.println("Dead!");
					}
				}
			}
		}

		this.nextDirection = targetDirection;
	}

	private boolean isSameAsOppositeOfLookingDirection(Direction checkDirection) {
		if(checkDirection != null) 
			return checkDirection.getOpposite() == aiSnake.getLookingDirection();
		return false;
	}

	private void findTargetItem() {
		List<Item> items = new ArrayList<>(Items.sortByUsefulness(GameFrame.getInstance().getGamePanel().getLevel().getItems()));
		/* #java1.8 */ //items.removeIf((Item item) -> item.getUsefulness() != items.get(items.size()-1).getUsefulness()); // ORIGINAL
		

		/* #java1.7 */ List<Item> toDelete = new ArrayList<Item>();
		/* #java1.7 */ int mostUsefulItem = items.get(items.size()-1).getUsefulness();
		/* #java1.7 */ for(int i=0;i<items.size();i++)
		/* #java1.7 */ 		if(items.get(i).getUsefulness() < mostUsefulItem)
		/* #java1.7 */ 			toDelete.add(items.get(i));
		/* #java1.7 */ items.removeAll(toDelete);

		target = Items.sortByDistance(items, aiSnake.getHead()).get(0);
	}
	
	private Direction findSafeDirection(Direction unsafe) {
		Point currentPosition = new Point(aiSnake.getHead().getPosition());
		List<CellObject> cellObjects = new ArrayList<CellObject>(Arrays.asList(GameFrame.getInstance().getGamePanel().getLevel().staticCellObjects));
		cellObjects.addAll(4, aiSnake.getSegments());
		
		Direction looking = aiSnake.getLookingDirection();
		for(Direction direction : Direction.values()) {
			if(direction != unsafe && direction != aiSnake.getLookingDirection().getOpposite()) {
				for(CellObject cellObject : cellObjects) {
					Point next = new Point((int) currentPosition.getX() + direction.getXOffset(), (int) currentPosition.getY() + direction.getYOffset());
					if(!cellObject.getPosition().equals(next))
						return direction;
				}
			}
		}
		
		return null;
	}
}