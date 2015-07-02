package model;

import java.awt.Point;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

import model.cellobject.SnakeSegment;
import view.GameCanvas;

/**
 * @author Eric Armbruster
 * @version 23.06.2015
 */
public class Snake {
	
	//TODO Check if there is something, when adding a segment 

	private static final int MIN_SEGMENTS_VALUE = 3, MIN_SCORE_VALUE = 0;

	private static final Predicate<Integer> MIN_SEGMENTS = t -> t >= MIN_SEGMENTS_VALUE;
	private static final Predicate<Integer> MIN_SCORE = t -> t >= MIN_SCORE_VALUE;

	private Deque<SnakeSegment> segments;
	private Direction lastDirection;
	private boolean directionChange = false, endless, AI;
	private int score = 0;

	/**
	 * Creates a new Snake instance.
	 * 
	 * @param startSegments
	 *            the amount of segments the snake has
	 * @param startPosition
	 *            the start position of the snake
	 * @param startDirection
	 *            the direction the snake is looking into
	 * @param endless
	 *            whether or not the level is endless
	 */
	public Snake(int startSegments, Point startPosition, Direction startDirection, boolean endless, boolean AI) {
		if (!MIN_SEGMENTS.test(startSegments))
			startSegments = MIN_SEGMENTS_VALUE;

		this.lastDirection = Objects.requireNonNull(startDirection);
		this.endless = endless;
		this.AI = AI;

		segments = new LinkedList<SnakeSegment>();

		segments.addFirst(new SnakeSegment(RotatedImage.get(startDirection, RotatedImage.HEAD_IMAGE), new Point(
				startPosition), startDirection));

		for (int i = 1; i < startSegments - 1; i++)
			segments.add(new SnakeSegment(RotatedImage.get(startDirection, RotatedImage.BODY_IMAGE), startPosition,
					startDirection, true, endless));

		segments.addLast(new SnakeSegment(RotatedImage.get(startDirection.getOpposite(), RotatedImage.TAIL_IMAGE),
				startPosition, startDirection, true, endless));
	}

	/**
	 * Creates a new Snake instance.
	 * 
	 * @param startSegments
	 *            the amount of segments the snake has
	 * @param startPosition
	 *            the start position of the snake
	 * @param startDirection
	 *            the direction the snake is looking into
	 * @param lives
	 *            the lives of the snake, default is 0
	 */
	public Snake(int startSegments, Point startPosition, Direction startDirection) {
		this(startSegments, startPosition, startDirection, false, false);
	}

	/**
	 * Returns the current score of the snake.
	 * 
	 * @return the score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Sets the current score of the snake.
	 * 
	 * @param score
	 *            the new score
	 */
	public void setScore(int score) {
		if (!MIN_SCORE.test(score))
			this.score = MIN_SCORE_VALUE;
		else
			this.score = score;
	}
	
	/**
	 * Increases the current score.
	 * 
	 * @param increaseBy
	 *            the value by that the score gets increased
	 */
	public void increaseScore(int increaseBy) {
		score += increaseBy;
	}
	
	/**
	 * Decreases the current score.
	 * 
	 * @param decreaseBy
	 *            the value by that the score gets decreased
	 */
	public void decreaseScore(int decreaseBy) {
		if (MIN_SCORE.test(score - decreaseBy))
			score -= decreaseBy;
		else
			score = MIN_SCORE_VALUE;
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
	 * Returns whether or not the snake is an AI.
	 * 
	 * @return true when the snake is an AI, false otherwise.
	 */
	public boolean isAI() {
		return AI;
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
	 * Removes the last segment from the snake.
	 * 
	 * @return true, when the last segment was removed and false otherwise, when
	 *         there are not enough segments left to remove one more.
	 */
	public boolean removeSegment() {
		if (!MIN_SEGMENTS.test(segments.size() - 1))
			return false;

		segments.removeLast();
		segments.getLast().setImage(RotatedImage.get(segments.getLast().getDirection().getOpposite(), RotatedImage.TAIL_IMAGE));
		return true;
	}

	/**
	 * Adds a segment to the end of the snake.
	 */
	public void addSegment() {
		SnakeSegment newBody = segments.getLast();
		newBody.setImage(RotatedImage.get(newBody.getDirection(), RotatedImage.BODY_IMAGE));
		segments.addLast(new SnakeSegment(RotatedImage.get(newBody.getDirection().getOpposite(),
				RotatedImage.TAIL_IMAGE), new Point(newBody.getPosition()), newBody.getDirection(), true, endless));
	}

	/**
	 * Moves the snake one cell forward into the direction its looking into, if
	 * possible.
	 * 
	 * @return true when the snake was moved, false otherwise.
	 */
	public boolean move() {
		SnakeSegment newBody = segments.getFirst();
		Point headPos = newBody.getPosition();
		if (!endless)
			if (headPos.getX() > GameCanvas.LEVEL_WIDTH - 1 || headPos.getY() > GameCanvas.LEVEL_HEIGHT - 1
					|| headPos.getX() < 0 || headPos.getY() < 0)
				return false;

		if (directionChange) {
			newBody.setImage(RotatedImage.getCurve(lastDirection, getLookingDirection()));
			directionChange = false;
		} else
			newBody.setImage(RotatedImage.get(newBody.getDirection(), RotatedImage.BODY_IMAGE));

		segments.addFirst(new SnakeSegment(RotatedImage.get(newBody.getDirection(), RotatedImage.HEAD_IMAGE),
				new Point(newBody.getPosition()), newBody.getDirection(), false, endless));

		segments.removeLast();
		segments.getLast().setImage(
				RotatedImage.get(segments.getLast().getDirection().getOpposite(), RotatedImage.TAIL_IMAGE));

		return true;
	}
}