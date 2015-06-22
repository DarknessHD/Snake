package de.schoolproject.snake;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.EventListener;

public abstract class CellObject {
	
	protected final BufferedImage image;
	protected Point position;
	private OnSnakeLeaveListener snakeLeaveListener;
	private OnSnakeEnterListener snakeEnterListener;
	
	public CellObject(BufferedImage image, Point position) {
		this.image = image;
		this.position = position;
	}
	
	public Point getPosition() {
		return this.position;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public void setOnSnakeEnterListener(OnSnakeEnterListener snakeEnterListener) {
		this.snakeEnterListener = snakeEnterListener;
	}
	
	public void setOnSnakeLeaveListener(OnSnakeLeaveListener snakeLeaveListener) {
		this.snakeLeaveListener = snakeLeaveListener;
	}
	
	public OnSnakeEnterListener getOnSnakeEnterListener() {
		return this.snakeEnterListener;
	}
	
	public OnSnakeLeaveListener getOnSnakeLeaveListener() {
		return this.snakeLeaveListener;
	}
	
	public interface OnSnakeEnterListener extends EventListener {
		void onSnakeEnterCell(SnakeEvent event); 
	}
	
	public interface OnSnakeLeaveListener extends EventListener {
		void onSnakeLeaveCell(SnakeEvent event); 
	}
}