package control.snakecontroller;

import model.Direction;
import model.Snake;
import view.GameFrame;

/**
 * @author Stefan Kameter, Eric Armbruster
 * @version 03.07.2015
 */
public class AIController implements SnakeController {
	
	@Override
	public Direction getDirection(int index) {
		Snake snake = GameFrame.getInstance().getGameCanvas().getSnakes()[index];
		return snake.getPathfinder().getNextDirection();
	}
}