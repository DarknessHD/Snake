package model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Eric
 * 23.06.2015
 */
public class Snake {
	
	private static final int MIN_SEGMENTS = 2;
	private static final String KEY_HEAD = "snakeHead", KEY_TAIL = "snakeTail", KEY_BODY_PART = "snakeBodyPart", KEY_BODY_PART_CURVED = "snakeBodyPartCurved";

	private Deque<SnakeSegment> segments;
	private Direction lastDirection;
	private boolean directionChange = false;
	
	/**
	 * Creates a new Snake instance with exactly two segments, the position at 0, 0 and Direction.DOWN.
	 */
	public Snake() {
		this(MIN_SEGMENTS, new Point(0, 0), Direction.DOWN);
	}
	
	/**
	 * Creates a new Snake instance with @param startSegments segments.
	 * 
	 * @param startSegments
	 * 					the amount of segments the snake has
	 * @param startPosition
	 * 					the start position of the snake
	 * @param startDirection
	 * 					the direction the snake is looking into
	 */
	public Snake(int startSegments, Point startPosition, Direction startDirection) {
		if(startSegments < MIN_SEGMENTS)
			throw new IllegalArgumentException("Snake must have at least " + MIN_SEGMENTS + "!");
		this.lastDirection = startDirection;
		
		segments = new LinkedList<SnakeSegment>();
		
		segments.addFirst(new SnakeSegment(ImageHolder.getImage(KEY_HEAD), startPosition, startDirection));
		
		Direction opposite = startDirection.getOpposite();
		BufferedImage snakeBodyPart = ImageHolder.getImage(KEY_BODY_PART);
		for(int i = 1; i < startSegments-1; i++)
			segments.add(new SnakeSegment(snakeBodyPart, getNextPosition(startPosition, opposite), startDirection));
		segments.addLast(new SnakeSegment(ImageHolder.getImage(KEY_TAIL), getNextPosition(startPosition, opposite), startDirection));
	}
	
	/**
	 * Returns the head segment of the snake.
	 * 
	 * @return the head segment
	 */
	public SnakeSegment getHead() {
		return segments.getFirst();
	}
	
	/**
	 * Returns the tail segment of the snake.
	 * 
	 * @return the tail segment
	 */
	public SnakeSegment getTail() {
		return segments.getLast();
	}
	
	/**
	 * Returns all body parts of the snake. 
	 * This doesn't contain the head and the tail.
	 * 
	 * @return the body parts of the snake
	 */
	public Deque<SnakeSegment> getBodyParts() {
		Deque<SnakeSegment> segments = getSegments();
		segments.removeFirst();
		segments.removeLast();
		return segments;
	}
	
	/**
	 * Returns all segments of the snake.
	 * 
	 * @return the segments of the snake
	 */
	public Deque<SnakeSegment> getSegments() {
		return new LinkedList<SnakeSegment>(segments);
	}
	
	/**
	 * Returns the direction the snake's head is looking into.
	 * 
	 * @return the direction of the snake's head
	 */
	public Direction getLookingDirection() {
		return segments.getFirst().getDirection();
	}
	
	/**
	 * Sets the direction the snake's head is looking into.
	 * 
	 * @param direction of the snake's head
	 */
	public void setLookingDirection(Direction direction) {
		SnakeSegment head = segments.getFirst();
		//TODO Rotate Images
		switch(direction) {
			case UP:
				head.setImage(null);
				break;
			case DOWN:
				head.setImage(null);
				break;
			case LEFT:
				head.setImage(null);
				break;
			case RIGHT:
				head.setImage(null);
				break;
		}
		directionChange = true;
		
		segments.getFirst().setDirection(direction);
	}
	
	/**
	 * Removes a segment from the snake.
	 */
	public void removeSegment() {
		if(segments.size() <= MIN_SEGMENTS)
			throw new IllegalStateException("Snake must have at least " + MIN_SEGMENTS + "!");
		
		segments.removeLast();
		segments.getLast().setImage(ImageHolder.getImage(KEY_TAIL));
	}
	
	/**
	 * Adds a segment to the snake.
	 */
	public void addSegment() {
		SnakeSegment tail = segments.getLast();
		tail.setImage(ImageHolder.getImage(KEY_BODY_PART));
		segments.addLast(new SnakeSegment(ImageHolder.getImage(KEY_TAIL), getNextPosition(tail.getPosition(), tail.getDirection().getOpposite()), tail.getDirection())); //TOOD New Tail
	}
	
	/**
	 * Moves the snake one cell forward into the direction its looking into.
	 */
	public void move() {
		SnakeSegment head = segments.getFirst();
		if(directionChange) {
			//TODO Rotate Image
			if(lastDirection == Direction.UP || getLookingDirection() == Direction.UP && lastDirection == Direction.LEFT || getLookingDirection() == Direction.LEFT);
			else if(lastDirection == Direction.DOWN || getLookingDirection() == Direction.DOWN && lastDirection == Direction.LEFT || getLookingDirection() == Direction.LEFT);
			else if(lastDirection == Direction.DOWN || getLookingDirection() == Direction.DOWN && lastDirection == Direction.RIGHT || getLookingDirection() == Direction.RIGHT);
			else if(lastDirection == Direction.UP || getLookingDirection() == Direction.UP && lastDirection == Direction.RIGHT || getLookingDirection() == Direction.RIGHT);
			else head.setImage(ImageHolder.getImage(KEY_BODY_PART));
			
			head.setImage(ImageHolder.getImage(KEY_BODY_PART_CURVED));
		}
		else
			head.setImage(ImageHolder.getImage(KEY_BODY_PART));
		segments.addFirst(new SnakeSegment(ImageHolder.getImage(KEY_HEAD), getNextPosition(head.getPosition(), head.getDirection()), head.getDirection()));
		
		removeSegment();
	}
	
	private static Point getNextPosition(Point startPosition, Direction direction) {
		startPosition.setLocation((int) startPosition.getX() + direction.getXOffset(), (int) startPosition.getY() + direction.getYOffset());
		return startPosition;
	}
}