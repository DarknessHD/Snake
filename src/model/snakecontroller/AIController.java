package model.snakecontroller;

import model.Direction;

/**
 * @author Stefan Kameter, Eric Armbruster
 *
 */
public class AIController implements SnakeController {

	@Override
	public Direction getDirection(int index) {
		// TODO Auto-generated method stub
		return null;
	}
//	private Snake[] snakes;
//	private ArrayList<CellObject> allObjects;
//	private List<Item> items;
//	private Point hp;
//
//	@Override
//	public Direction getDirection(int index) {
//		Direction dir = null;
//
//		snakes = GameFrame.getInstance().getGameCanvas().getSnakes();
//		items = GameFrame.getInstance().getGameCanvas().getItems();
//
//		allObjects = new ArrayList<CellObject>(GameFrame.getInstance().getGameCanvas().getStaticObjects());
//		allObjects.addAll(snakes[(index + 1) % 2].getSegments());
//		Deque<SnakeSegment> td = snakes[index].getSegments();
//		for (int i = 0; i < 3; i++)
//			td.removeFirst();
//		allObjects.addAll(td);
//
//		hp = snakes[index].getHead().getPosition();
//
//		Item target = null;
//		for (Item item : items)
//			if (target == null)
//				target = item;
//			else if (hp.distance(target.getPosition()) > hp.distance(item.getPosition()))
//				target = item;
//
//		Point tp = target.getPosition();
//
//		int r = (int) (Math.random() * 2);
//
//		switch (r) {
//		case 0:
//			if (tp.x > hp.x)
//				dir = Direction.RIGHT;
//			else
//				dir = Direction.LEFT;
//			break;
//		case 1:
//			if (tp.y > hp.y)
//				dir = Direction.DOWN;
//			else
//				dir = Direction.UP;
//			break;
//		}
//		
//		
//		dir = checkForNearbyObjects(dir);
//
//		return dir;
//	}
//
//	private Direction checkForNearbyObjects(Direction dir) {
//		for (CellObject obj : allObjects)
//			switch (dir) {
//			case UP:
//				int d = obj.getPosition().y - hp.y;
//				if (d > 0 && d < 2)
//					dir = dir.getNext();
//				break;
//			case RIGHT:
//				d = obj.getPosition().x - hp.x;
//				if (d < 0 && d > -2) {
//					dir = dir.getNext();
//					return dir;
//				}
//			case DOWN:
//				d = obj.getPosition().y - hp.y;
//				if (d < 0 && d > -2) {
//					dir = dir.getNext();
//					return dir;
//				}
//			case LEFT:
//				d = obj.getPosition().x - hp.x;
//				if (d > 0 && d < 2)
//					dir = dir.getNext();
//					return dir;
//				}
//			}
//	}
}