package control.snakecontroller;

import java.util.List;

import model.Direction;
import model.Snake;
import view.GameFrame;

/**
 * @author Stefan Kameter, Eric Armbruster
 * @version 03.07.2015
 */
public class AIController implements SnakeController {
	
	private List<Direction> path;
	
	@Override
	public Direction getDirection(int index) {
		Snake snake = GameFrame.getInstance().getGameCanvas().getSnakes()[index];
		if(snake.getPathfinder().getPath() == null) {
			Pathfinder pathfinder = snake.getPathfinder();
			
			pathfinder.setTarget(pathfinder.findNearestItem(GameFrame.getInstance().getGameCanvas().getItems()));
			pathfinder.setPath(pathfinder.findPath());
		}
		path = snake.getPathfinder().getPath().getDirections();
		
		Direction next = path.get(0);
		path.remove(0);
		return next;
	}
}