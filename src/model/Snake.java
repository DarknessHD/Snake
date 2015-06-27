package model;

import java.awt.Point;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

import view.GameCanvas;

/**
 * @author Eric Armbruster
 * @version 23.06.2015
 */
public class Snake {

	private static final int MIN_SEGMENTS = 2, MIN_SPEED = 1, MIN_LIVES = 0;

	private Deque<SnakeSegment> segments;
	private Direction lastDirection;
	private boolean directionChange = false;
	private int speed = 1, lives = 1;

	/**
	 * Creates a new Snake instance with @param startSegments segments.
	 * 
	 * @param startSegments
	 *            the amount of segments the snake has
	 * @param startPosition
	 *            the start position of the snake
	 * @param startDirection
	 *            the direction the snake is looking into
	 * @param speed
	 *            the speed of the snake, default is 10
	 * @param lives
	 *            the lives of the snake, default is 0
	 */
	public Snake(int startSegments, Point startPosition, Direction startDirection, int speed, int lives) {
		if (startSegments < MIN_SEGMENTS)
			throw new IllegalArgumentException("Snake must have at least " + MIN_SEGMENTS + "!");
		if (speed < MIN_SPEED)
			throw new IllegalArgumentException("Snake must have at least a speed of " + MIN_SPEED + "!");
		if (lives < MIN_LIVES)
			throw new IllegalArgumentException("Snake must have at least " + MIN_LIVES + " lives!");

		this.lastDirection = Objects.requireNonNull(startDirection);
		this.speed = speed;
		this.lives = lives;

		segments = new LinkedList<SnakeSegment>();

		segments.addFirst(new SnakeSegment(RotatedImage.get(startDirection, RotatedImage.HEAD_IMAGE), new Point(
				startPosition), startDirection));

		Direction opposite = startDirection.getOpposite();
		for (int i = 1; i < startSegments - 1; i++)
			segments.add(new SnakeSegment(RotatedImage.get(startDirection, RotatedImage.BODY_IMAGE), getNextPosition(
					startPosition, opposite), startDirection));
		segments.addLast(new SnakeSegment(RotatedImage.get(opposite, RotatedImage.TAIL_IMAGE), getNextPosition(
				startPosition, opposite), startDirection));
	}

	/**
	 * Creates a new Snake instance with @param startSegments segments, @param
	 * speed 10, @param lives 0.
	 * 
	 * @param startSegments
	 *            the amount of segments the snake has
	 * @param startPosition
	 *            the start position of the snake
	 * @param startDirection
	 *            the direction the snake is looking into
	 * @param speed
	 *            the speed of the snake, default is 10
	 * @param lives
	 *            the lives of the snake, default is 0
	 */
	public Snake(int startSegments, Point startPosition, Direction startDirection) {
		this(startSegments, startPosition, startDirection, 10, 0);
	}

	/**
	 * Returns the speed of the snake.
	 * 
	 * @return the speed
	 */
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * Sets the speed of the snake.
	 * 
	 * @param speed
	 *            the speed
	 */
	public void setSpeed(int speed) {
		if (speed < MIN_SPEED)
			throw new IllegalArgumentException("Snake must have at least a speed of " + MIN_SPEED + "!");
		this.speed = speed;
	}

	/**
	 * Returns how many lives the snake has left.
	 * 
	 * @return the lives
	 */
	public int getLives() {
		return this.lives;
	}

	/**
	 * Sets the amount of lives the snake has.
	 * 
	 * @param lives
	 *            the lives
	 */
	public void setLives(int lives) {
		if (lives < MIN_LIVES)
			throw new IllegalArgumentException("Snake must have at least " + MIN_LIVES + " lives");
		this.lives = lives;
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
	 * Returns all body parts of the snake. This doesn't contain the head and
	 * the tail.
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
	 * IMPORTANT: Always use this method to change the direction of the snake's
	 * head! Sets the direction the snake's head is looking into.
	 * 
	 * @param direction
	 *            of the snake's head
	 */
	public void setLookingDirection(Direction direction) {
		SnakeSegment head = segments.getFirst();
		Direction currentDirection = head.getDirection();
		if (direction != currentDirection && direction != currentDirection.getOpposite()) {
			this.lastDirection = head.getDirection();
			head.setImage(RotatedImage.get(direction, RotatedImage.HEAD_IMAGE));
			directionChange = true;

			head.setDirection(direction);
		}
	}

	/**
	 * Removes a segment from the snake.
	 */
	public void removeSegment() {
		if (segments.size() <= MIN_SEGMENTS)
			throw new IllegalStateException("Snake must have at least " + MIN_SEGMENTS + "!");

		segments.removeLast();
		segments.getLast().setImage(RotatedImage.get(segments.getLast().getDirection(), RotatedImage.TAIL_IMAGE));
	}

	/**
	 * Adds a segment to the snake.
	 */
	public void addSegment() {
		SnakeSegment newBody = segments.getLast();
		newBody.setImage(RotatedImage.get(newBody.getDirection(), RotatedImage.BODY_IMAGE));
		segments.addLast(new SnakeSegment(RotatedImage.get(segments.getLast().getDirection(), RotatedImage.TAIL_IMAGE),
				getNextPosition(newBody.getPosition(), newBody.getDirection().getOpposite()), newBody.getDirection()));
	}

	/**
	 * Returns whether or not the snake has reached the edge of the map.
	 * 
	 * @return true when the snake has reached the edge of the map, false
	 *         otherwise.
	 */
	public boolean hasReachedEdge() {
		Point headPos = segments.getFirst().getPosition();
		return (headPos.getX() >= GameCanvas.LEVEL_WIDTH || headPos.getY() >= GameCanvas.LEVEL_HEIGHT
				|| headPos.getX() <= 0 || headPos.getY() <= 0);
	}

	/**
	 * Moves the snake one cell forward into the direction its looking into, if
	 * possible.
	 * 
	 * @param endlessLevel
	 *            whether or not the level is endless
	 * 
	 * @return true when the snake was moved, false otherwise.
	 */
	public boolean move(boolean endlessLevel) {
		if (!endlessLevel)
			if (hasReachedEdge())
				return false;

		SnakeSegment newBody = segments.getFirst();

		if (directionChange) {
			newBody.setImage(RotatedImage.getCurve(lastDirection, getLookingDirection()));
			directionChange = false;
		} else
			newBody.setImage(RotatedImage.get(newBody.getDirection(), RotatedImage.BODY_IMAGE));

		segments.addFirst(new SnakeSegment(RotatedImage.get(newBody.getDirection(), RotatedImage.HEAD_IMAGE),
				getNextPosition(new Point(newBody.getPosition()), newBody.getDirection(), endlessLevel), newBody
						.getDirection()));

		segments.removeLast();
		segments.getLast().setImage(
				RotatedImage.get(segments.getLast().getDirection().getOpposite(), RotatedImage.TAIL_IMAGE));
		return true;
	}

	/**
	 * Moves the snake one cell forward into the direction its looking into, if
	 * possible.
	 * 
	 * @return true when the snake was moved, false otherwise.
	 */
	public boolean move() {
		return move(false);
	}

	private static Point getNextPosition(Point startPosition, Direction direction) {
		startPosition.setLocation((int) startPosition.getX() + direction.getXOffset(), (int) startPosition.getY()
				+ direction.getYOffset());

		return new Point(startPosition);
	}

	private static Point getNextPosition(Point startPosition, Direction direction, boolean endlessLevel) {
		Point next = getNextPosition(startPosition, direction);

		if (endlessLevel) {
			if (next.getX() < 0)
				next.setLocation(GameCanvas.LEVEL_WIDTH, next.getY());
			else if (next.getY() < 0)
				next.setLocation(next.getX(), GameCanvas.LEVEL_HEIGHT);
			else if (next.getX() > GameCanvas.LEVEL_WIDTH)
				next.setLocation(0, next.getY());
			else if (next.getY() > GameCanvas.LEVEL_HEIGHT)
				next.setLocation(next.getX(), 0);
		}

		return next;
	}
}