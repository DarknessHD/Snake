package control.snakecontroller;

import model.Direction;
import model.Snake;
import view.GameFrame;

/**
 * @author Stefan Kameter, Eric Armbruster
 * @version 08.07.2015
 */
public class AIController implements SnakeController {

	private int count = 0;
	@Override
	public Direction getDirection(int index) {
		Snake snake = GameFrame.getInstance().getGamePanel().getLevel().snakes[index];
		System.out.println(count++);
		return snake.getPathfinder().getNextDirection();
	}
}