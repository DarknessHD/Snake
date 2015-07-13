package model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Objects;

import view.GameFrame;
import control.Constants;
import control.snakecontroller.Pathfinder;

/**
 * @author Eric Armbruster
 * @version 23.06.2015
 */
public class Snake {
	
	//TODO getAdjacent behavior has changed

	private static final int MIN_SCORE_VALUE = 0;

	// /* #java1.8 */ private static final Predicate<Integer> MIN_SEGMENTS = t -> t >= Constants.MIN_SEGMENTS; // ORIGINAL
	// /* #java1.8 */ private static final Predicate<Integer> MIN_SCORE = t -> t >= MIN_SCORE_VALUE; // ORIGINAL

	private LinkedList<SnakeSegment> segments;
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
		 // /* #java1.8 */ if (!MIN_SEGMENTS.test(startSegments)) // ORIGINAL
		 /* #java1.7 */ if(startSegments < Constants.MIN_SEGMENTS)
			startSegments = Constants.MIN_SEGMENTS;

		this.lastDirection = Objects.requireNonNull(startDirection);

		segments = new LinkedList<SnakeSegment>();

		segments.addFirst(new SnakeSegment(RotatedImage.get(startDirection, RotatedImage.HEAD_IMAGE), new TilePosition(startPosition), startDirection));

		for (int i = 1; i < startSegments - 1; i++)
			segments.add(new SnakeSegment(RotatedImage.get(startDirection, RotatedImage.BODY_IMAGE), startPosition, startDirection, true));

		segments.addLast(new SnakeSegment(RotatedImage.get(startDirection.getOpposite(), RotatedImage.TAIL_IMAGE), startPosition, startDirection, true));
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
		 /* #java1.8 */ // if (!MIN_SCORE.test(score)) // ORIGINAL
		 /* #java1.7 */ if(this.score < MIN_SCORE_VALUE)
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
		 /* #java1.8 */ // if (MIN_SCORE.test(score - decreaseBy)) // ORIGINAL
		 /* #java1.7 */ if(!(score-decreaseBy < MIN_SCORE_VALUE))
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
	 * Returns all body parts of the snake. This doesn't contain the head and the tail.
	 * 
	 * @return the body parts of the snake
	 */
	public LinkedList<SnakeSegment> getBodyParts() {
		LinkedList<SnakeSegment> segments = getSegments();
		segments.removeFirst();
		segments.removeLast();
		return segments;
	}

	/**
	 * Returns all segments of the snake.
	 * 
	 * @return the segments of the snake
	 */
	public LinkedList<SnakeSegment> getSegments() {
		return new LinkedList<SnakeSegment>(segments);
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
		return segments.getFirst().getDirection();
	}

	/**
	 * IMPORTANT: Always use this method to change the direction of the snake's head! Sets the direction the snake's head is looking into.
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
	 * @return true, when the last segment was removed and false otherwise, when there are not enough segments left to remove one more.
	 */
	public boolean removeSegment() {
		 /* #java1.8 */ // if (!MIN_SEGMENTS.test(segments.size() - 1)) { // ORIGINAL
		 /* #java1.7 */ if(segments.size()-1 < Constants.MIN_SEGMENTS) {
			GameFrame.getInstance().stop();
			return false;
		}

		TilePosition position = segments.removeLast().getPosition();
		segments.getLast().setImage(RotatedImage.get(segments.getLast().getDirection().getOpposite(), RotatedImage.TAIL_IMAGE));
		GameFrame.getInstance().getGamePanel().doRepaint(position);
		return true;
	}

	/**
	 * Adds a segment to the end of the snake.
	 */
	public void addSegment() {
		SnakeSegment newBody = segments.getLast();
		newBody.setImage(RotatedImage.get(newBody.getDirection(), RotatedImage.BODY_IMAGE));
		segments.addLast(new SnakeSegment(RotatedImage.get(newBody.getDirection().getOpposite(), RotatedImage.TAIL_IMAGE), new TilePosition(newBody.getPosition()), newBody.getDirection(), true));
		GameFrame.getInstance().getGamePanel().doRepaint(newBody.getPosition());
	}

	/**
	 * Moves the snake one cell forward into the direction its looking into, if possible.
	 * 
	 * @return true when the snake was moved, false otherwise.
	 */
	public boolean move() {
		SnakeSegment newBody = segments.getFirst();
		Point headPos = newBody.getPosition();
		if (!GameFrame.getInstance().getGamePanel().getLevel().endless)
			if (headPos.getX() > Constants.LEVEL_WIDTH - 1 || headPos.getY() > Constants.LEVEL_HEIGHT - 1 || headPos.getX() < 0 || headPos.getY() < 0)
				return false;

		if (directionChange) {
			newBody.setImage(RotatedImage.getCurve(lastDirection, getLookingDirection()));
			directionChange = false;
		} else
			newBody.setImage(RotatedImage.get(newBody.getDirection(), RotatedImage.BODY_IMAGE));

		segments.addFirst(new SnakeSegment(RotatedImage.get(newBody.getDirection(), RotatedImage.HEAD_IMAGE), new TilePosition(newBody.getPosition()), newBody.getDirection(), false));

		segments.removeLast();
		segments.getLast().setImage(RotatedImage.get(segments.getLast().getDirection().getOpposite(), RotatedImage.TAIL_IMAGE));

		return true;
	}

	@Override
	public Snake clone() {
		Snake snake = new Snake(segments.size(), new TilePosition(getHead().position), getHead().getDirection());
		if(this.pathfinder != null)
			snake.setPathfinder();
		return snake;
	}
}
