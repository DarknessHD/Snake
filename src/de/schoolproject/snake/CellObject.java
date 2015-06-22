package de.schoolproject.snake;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.EventListener;

/**
 * @author Eric Armbruster
 * @version 22.06.2015
 */
public abstract class CellObject {

	protected final BufferedImage image;
	protected Point position;
	private OnSnakeLeaveListener snakeLeaveListener;
	private OnSnakeEnterListener snakeEnterListener;

	/**
	 * Subclasses can use this constructor to create a new CellObject.
	 * 
	 * @param image - the image of the CellObject
	 * @param position - the position of the CellObject
	 */
	public CellObject(BufferedImage image, Point position) {
		this.image = image;
		this.position = position;
	}

	/**
	 * Returns the position of the CellObject.
	 * 
	 * @return - the position of the CellObject
	 */
	public Point getPosition() {
		return this.position;
	}

	/**
	 * Sets the position of the CellObject.
	 * 
	 * @param position - the position of the CellObject
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * Returns the image of the CellObject.
	 * 
	 * @return - the BufferedImage of the CellObject
	 */
	public BufferedImage getImage() {
		return this.image;
	}

	/**
	 * Sets the snake enter listener of the CellObject.
	 * 
	 * @param snakeEnterListener - the OnSnakeEnterListener of the CellObject
	 */
	public void setOnSnakeEnterListener(OnSnakeEnterListener snakeEnterListener) {
		this.snakeEnterListener = snakeEnterListener;
	}

	/**
	 * Sets the snake leave listener of the CellObject.
	 * 
	 * @param snakeLeaveListener - the OnSnakeLeaveListener of the CellObject
	 */
	public void setOnSnakeLeaveListener(OnSnakeLeaveListener snakeLeaveListener) {
		this.snakeLeaveListener = snakeLeaveListener;
	}
	
	/**
	 * Returns the snake enter listener.
	 * 
	 * @return - the snake enter listener
	 */
	public OnSnakeEnterListener getOnSnakeEnterListener() {
		return this.snakeEnterListener;
	}

	/**
	 * Returns the snake leave listener.
	 * 
	 * @return - the snake leave listener
	 */
	public OnSnakeLeaveListener getOnSnakeLeaveListener() {
		return this.snakeLeaveListener;
	}

	/**
	 * @author Eric Armbruster
	 * @version 22.06.2015
	 */
	public interface OnSnakeEnterListener extends EventListener {
		/**
		 * Called when a snake enters a Cell.
		 * 
		 * @param event - the snake event
		 */
		void onSnakeEnterCell(SnakeEvent event);
	}

	/**
	 * @author Eric Armbruster
	 * @version 22.06.2015
	 */
	public interface OnSnakeLeaveListener extends EventListener {
		/**
		 * Called when a snake leaves a Cell.
		 * 
		 * @param event - the snake event
		 */
		void onSnakeLeaveCell(SnakeEvent event);
	}
}