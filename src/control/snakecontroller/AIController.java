package control.snakecontroller;

import model.Direction;
import model.Snake;
import view.GameFrame;

/**
 * @author Stefan Kameter, Eric Armbruster
 * @version 08.07.2015
 */
public class AIController implements SnakeController {

	@Override
	public Direction getDirection(int index) {
		Snake snake = GameFrame.getInstance().getGamePanel().getLevel().snakes[index];
		return snake.getPathfinder().getNextDirection();
	}
}