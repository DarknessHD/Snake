package control.snakecontroller;

import input.KeyBoard;
import model.Direction;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class PlayerController implements SnakeController {
	private static KeyBoard keyBoard;

	@Override
	public Direction getDirection(int index) {
		if (keyBoard == null)
			keyBoard = KeyBoard.getInstance();

		if (keyBoard.isKeyPressed(KeyBoard.UP[index]))
			return Direction.UP;
		else if (keyBoard.isKeyPressed(KeyBoard.RIGHT[index]))
			return Direction.RIGHT;
		else if (keyBoard.isKeyPressed(KeyBoard.DOWN[index]))
			return Direction.DOWN;
		else if (keyBoard.isKeyPressed(KeyBoard.LEFT[index]))
			return Direction.LEFT;
		return null;
	}
}