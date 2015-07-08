package control.snakecontroller;

import input.KeyBoard;
import model.Direction;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class PlayerController implements SnakeController {
	@Override
	public Direction getDirection(int index) {
		if (KeyBoard.getInstance().isKeyPressed(KeyBoard.UP[index]))
			return Direction.UP;
		else if (KeyBoard.getInstance().isKeyPressed(KeyBoard.RIGHT[index]))
			return Direction.RIGHT;
		else if (KeyBoard.getInstance().isKeyPressed(KeyBoard.DOWN[index]))
			return Direction.DOWN;
		else if (KeyBoard.getInstance().isKeyPressed(KeyBoard.LEFT[index]))
			return Direction.LEFT;
		return null;
	}
}