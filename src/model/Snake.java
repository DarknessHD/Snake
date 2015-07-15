package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import model.SnakeSegment.SegmentType;
import view.GameFrame;
import control.Constants;
import control.snakecontroller.Pathfinder;

/**
 * @author Eric Armbruster
 * @version 23.06.2015
 */
public class Snake {

	private static final int MIN_SCORE_VALUE = 0;

	/* #java1.8 */private static final Predicate<Integer> MIN_SEGMENTS = t -> t >= Constants.MIN_SEGMENTS; // ORIGINAL
	/* #java1.8 */private static final Predicate<Integer> MIN_SCORE = t -> t >= MIN_SCORE_VALUE; // ORIGINAL

	private List<SnakeSegment> segments;
	private Direction lastDirection;
	private boolean directionChange = false;
	private int score = 0;
	private Pathfinder pathfinder = null;

	/**
	 * Creates a new Snake instance.
	 * 
	 * @param startSegments
	 *            the amount of segments the snake has
	 * @param startPosition
	 *            the start position of the snake
	 * @param startDirection
	 *            the direction the snake is looking into
	 */
	public Snake(int startSegments, TilePosition startPosition, Direction startDirection) {
		/* #java1.8 */if (!MIN_SEGMENTS.test(startSegments)) // ORIGINAL
		// /* #java1.7 */if (startSegments < Constants.MIN_SEGMENTS)
			startSegments = Constants.MIN_SEGMENTS;

		this.lastDirection = Objects.requireNonNull(startDirection);
		segments = Collections.synchronizedList(new LinkedList<SnakeSegment>());

		segments.add(0, new SnakeSegment(startPosition, startDirection, SegmentType.SNAKE_HEAD));

		for (int i = 1; i < startSegments - 1; i++) {
			startPosition.setAdjacent(startDirection.getOpposite());
			segments.add(new SnakeSegment(startPosition, startDirection, SegmentType.SNAKE_BODY));
		}

		startPosition.setAdjacent(startDirection.getOpposite());
		segments.add(new SnakeSegment(startPosition, startDirection, SegmentType.SNAKE_TAIL));
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
		/* #java1.8 */if (!MIN_SCORE.test(score)) // ORIGINAL
		// /* #java1.7 */if (this.score < MIN_SCORE_VALUE)
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
		/* #java1.8 */if (MIN_SCORE.test(score - decreaseBy)) // ORIGINAL
		// /* #java1.7 */if (!(score - decreaseBy < MIN_SCORE_VALUE))
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
		return segments.get(0);
	}

	/**
	 * Returns the tail segment of the snake.
	 * 
	 * @return the tail segment
	 */
	public SnakeSegment getTail() {
		return segments.get(segments.size() - 1);
	}

	/**
	 * Returns all body parts of the snake. This doesn't contain the head and the tail.
	 * 
	 * @return the body parts of the snake
	 */
	public List<SnakeSegment> getBodyParts() {
		List<SnakeSegment> segments = getSegments();
		segments.remove(0);
		segments.remove(segments.size() - 1);
		return segments;
	}

	/**
	 * Returns all segments of the snake.
	 * 
	 * @return the segments of the snake
	 */
	public List<SnakeSegment> getSegments() {
		return Collections.synchronizedList(new LinkedList<SnakeSegment>(segments));
	}

	/**
	 * Sets the Pathfinder for this snake.
	 */
	public void setPathfinder() {
		pathfinder = new Pathfinder(this);
	}

	/**
	 * Returns the Pathfinder of this snake.
	 * 
	 * @return the Pathfinder
	 */
	public Pathfinder getPathfinder() {
		return pathfinder;
	}

	/**
	 * Returns the direction the snake's head is looking into.
	 * 
	 * @return the direction of the snake's head
	 */
	public Direction getLookingDirection() {
		return segments.get(0).getDirection();
	}

	/**
	 * IMPORTANT: Always use this method to change the direction of the snake's head! Sets the direction the snake's head is looking into.
	 * 
	 * @param direction
	 *            of the snake's head
	 */
	public void setLookingDirection(Direction direction) {
		SnakeSegment head = segments.get(0);
		Direction currentDirection = head.getDirection();
		if (direction != currentDirection && direction != currentDirection.getOpposite()) {
			this.lastDirection = head.getDirection();
			head.setSegmentType(SegmentType.SNAKE_HEAD);
			directionChange = true;

			head.setDirection(direction);
		}
	}

	/**
	 * Removes the last segment from the snake.
	 * 
	 * @return true, when the last segment was removed and false otherwise, when there are not enough segments left to remove one more.
	 */
	public boolean removeSegment() {
		/* #java1.8 */if (!MIN_SEGMENTS.test(segments.size() - 1)) { // ORIGINAL
			// /* #java1.7 */if (segments.size() - 1 < Constants.MIN_SEGMENTS) {
			GameFrame.getInstance().stop();
			return false;
		}

		TilePosition position = segments.remove(segments.size() - 1).getPosition();
		getTail().setSegmentType(SegmentType.SNAKE_TAIL);
		GameFrame.getInstance().getGamePanel().doRepaint(position);
		return true;
	}

	/**
	 * Adds a segment to the end of the snake.
	 * 
	 * @param amount
	 *            the amount of segments to add
	 */
	public void addSegments(int amount) {
		for (int i = 0; i < amount; i++) {
			SnakeSegment newBody = getTail();
			newBody.setSegmentType(SegmentType.SNAKE_BODY);
			TilePosition last = new TilePosition(newBody.getPosition());
			last.setAdjacent(newBody.getDirection().getOpposite());

			if (i == amount - 1)
				segments.add(new SnakeSegment(last, newBody.getDirection(), SegmentType.SNAKE_TAIL));
			else
				segments.add(new SnakeSegment(last, newBody.getDirection(), SegmentType.SNAKE_BODY));

			GameFrame.getInstance().getGamePanel().doRepaint(newBody.getPosition());
			GameFrame.getInstance().getGamePanel().doRepaint(getTail().getPosition());
		}
	}

	/**
	 * Moves the snake one cell forward into the direction its looking into, if possible.
	 * 
	 * @return true when the snake was moved, false otherwise.
	 */
	public boolean move() {
		SnakeSegment newBody = getHead();
		TilePosition headPos = new TilePosition(newBody.getPosition());
		if (!GameFrame.getInstance().getGamePanel().getLevel().endless)
			if (!TilePosition.isWithinGameField(headPos))
				return false;

		if (directionChange) {
			newBody.setSegmentType(SegmentType.SNAKE_CURVE, lastDirection, getLookingDirection());
			directionChange = false;
		} else
			newBody.setSegmentType(SegmentType.SNAKE_BODY);

		headPos.setAdjacent(newBody.getDirection());
		segments.add(0, new SnakeSegment(headPos, newBody.getDirection(), SegmentType.SNAKE_HEAD));

		segments.remove(segments.size() - 1);
		getTail().setSegmentType(SegmentType.SNAKE_TAIL);

		return true;
	}

	@Override
	public Snake clone() {
		Snake snake = new Snake(segments.size(), new TilePosition(getHead().position), getHead().getDirection());
		if (this.pathfinder != null)
			snake.setPathfinder();
		return snake;
	}
}
