package model.cellobject;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Direction;
import model.Item;
import model.Snake;

public class Pathfinder {

	private Item target;
	private Snake snake;

	public Pathfinder(Snake snake) {
		this.snake = snake;
	}

	public void setTarget(Item target) {
		this.target = target;
	}

	public Item getTarget() {
		return target;
	}

	public List<Point> findPath() {
		if (target == null)
			return null;

		Point headPosition = snake.getHead().getPosition();
		Point targetPosition = target.getPosition();
		List<Point> path = new ArrayList<Point>();

		// Find the target directions
		Direction targetDirectionHorizontal = null;
		Direction targetDirectionVertical = null;

		int xDistance = (int) (headPosition.getX() - targetPosition.getX());
		int yDistance = (int) (headPosition.getY() - targetPosition.getY());

		if (xDistance > 0)
			targetDirectionHorizontal = Direction.LEFT;
		else if (xDistance < 0)
			targetDirectionHorizontal = Direction.RIGHT;
			

		if (yDistance > 0)
			targetDirectionVertical = Direction.UP;
		else if (yDistance < 0)
			targetDirectionVertical = Direction.DOWN;

		// Get the resulting target direction
		Direction target;
		if(targetDirectionHorizontal != null && targetDirectionVertical != null);
		
		return null;
	}

	public Item findNearestItem(Map<Point, ? extends Item> items) {
		Point nearestPosition = null;
		Point headPosition = snake.getHead().getPosition();

		for (Point position : items.keySet())
			if (nearestPosition == null || position.distance(headPosition) < nearestPosition.distance(headPosition))
				nearestPosition = position;

		return items.get(nearestPosition);
	}
}