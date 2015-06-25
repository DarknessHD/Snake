package model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Deque;
import java.util.LinkedList;

import view.GameCanvas;

/**
 * @author Eric Armbruster
 * @version 23.06.2015
 */
public class Snake {

	private static final int MIN_SEGMENTS = 2;
	private static final String HEAD_IMAGE = "snake_head", TAIL_IMAGE = "snake_tail", BODY_IMAGE = "snake_body", CURVE_IMAGE = "snake_curve";

	private Deque<SnakeSegment> segments;
	private Direction lastDirection;
	private boolean directionChange = false;

	/**
	 * Creates a new Snake instance with @param startSegments segments.
	 * 
	 * @param startSegments
	 *            the amount of segments the snake has
	 * @param startPosition
	 *            the start position of the snake
	 * @param startDirection
	 *            the direction the snake is looking into
	 */
	public Snake(int startSegments, Point startPosition, Direction startDirection) {
		if (startSegments < MIN_SEGMENTS)
			throw new IllegalArgumentException("Snake must have at least " + MIN_SEGMENTS + "!");
		this.lastDirection = startDirection;

		segments = new LinkedList<SnakeSegment>();

		segments.addFirst(new SnakeSegment(getRotatedHeadImage(startDirection), new Point(startPosition), startDirection));

		Direction opposite = startDirection.getOpposite();
		for (int i = 1; i < startSegments - 1; i++)
			segments.add(new SnakeSegment(BODY_IMAGE, new Point(getNextPosition(startPosition, opposite)), startDirection));
		segments.addLast(new SnakeSegment(TAIL_IMAGE, new Point(getNextPosition(startPosition, opposite)), startDirection));
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
	 * @param direction
	 *            of the snake's head
	 */
	public void setLookingDirection(Direction direction) {
		SnakeSegment head = segments.getFirst();
		String headImage = getRotatedHeadImage(direction);
		head.setImage(headImage);
		directionChange = true;

		segments.getFirst().setDirection(direction);
	}

	/**
	 * Removes a segment from the snake.
	 */
	public void removeSegment() {
		if (segments.size() <= MIN_SEGMENTS)
			throw new IllegalStateException("Snake must have at least " + MIN_SEGMENTS + "!");

		segments.removeLast();
		segments.getLast().setImage(TAIL_IMAGE);
	}

	/**
	 * Adds a segment to the snake.
	 */
	public void addSegment() {
		SnakeSegment tail = segments.getLast();
		tail.setImage(BODY_IMAGE);
		segments.addLast(new SnakeSegment(TAIL_IMAGE, getNextPosition(tail.getPosition(), tail.getDirection().getOpposite()), tail.getDirection())); // TOOD New Tail
	}

	/**
	 * Moves the snake one cell forward into the direction its looking into.
	 * 
	 * @param direction
	 *            the new direction
	 */
	public void move(Direction direction) {
		setLookingDirection(direction);

		SnakeSegment head = segments.getFirst();
		if (directionChange) {
			String curveImage = CURVE_IMAGE;
			if (lastDirection == Direction.DOWN || getLookingDirection() == Direction.DOWN && lastDirection == Direction.LEFT || getLookingDirection() == Direction.LEFT)
				curveImage = curveImage + "DOWN_LEFT";
			else if (lastDirection == Direction.DOWN || getLookingDirection() == Direction.DOWN && lastDirection == Direction.RIGHT || getLookingDirection() == Direction.RIGHT)
				curveImage = curveImage + "DOWN_RIGHT";
			else if (lastDirection == Direction.UP || getLookingDirection() == Direction.UP && lastDirection == Direction.RIGHT || getLookingDirection() == Direction.RIGHT)
				curveImage = curveImage + "UP_RIGHT";

			if (!ImageHolder.isLoaded(curveImage)) {
				BufferedImage curve = ImageHolder.getImage(CURVE_IMAGE);

				if (lastDirection == Direction.DOWN || getLookingDirection() == Direction.DOWN && lastDirection == Direction.LEFT || getLookingDirection() == Direction.LEFT)
					curve = GameCanvas.mirrorImage(curve, GameCanvas.HORIZONTAL);
				else if (lastDirection == Direction.DOWN || getLookingDirection() == Direction.DOWN && lastDirection == Direction.RIGHT || getLookingDirection() == Direction.RIGHT)
					curve = GameCanvas.mirrorImage(curve, GameCanvas.DIAGONAL);
				else if (lastDirection == Direction.UP || getLookingDirection() == Direction.UP && lastDirection == Direction.RIGHT || getLookingDirection() == Direction.RIGHT)
					curve = GameCanvas.mirrorImage(curve, GameCanvas.VERTICAL);
				ImageHolder.putImage(curveImage, curve);
			}

			head.setImage(curveImage);
		} else
			head.setImage(BODY_IMAGE);
		segments.addFirst(new SnakeSegment(getRotatedHeadImage(head.getDirection()), getNextPosition(head.getPosition(), head.getDirection()), head.getDirection()));

		removeSegment();
	}

	private static String getRotatedHeadImage(Direction headDirection) {
		BufferedImage head = ImageHolder.getImage(HEAD_IMAGE);

		String headImage = HEAD_IMAGE + headDirection.toString();
		if (!ImageHolder.isLoaded(headImage)) {
			switch (headDirection) {
			case UP:
				head = GameCanvas.mirrorImage(head, GameCanvas.HORIZONTAL);
				break;
			case DOWN:
				head = GameCanvas.mirrorImage(head, GameCanvas.VERTICAL);
				break;
			case LEFT:
				head = GameCanvas.mirrorImage(head, GameCanvas.DIAGONAL);
				break;
			case RIGHT:
				break;
			}
			ImageHolder.putImage(headImage, head);
		}

		return headImage;
	}

	private static Point getNextPosition(Point startPosition, Direction direction) {
		startPosition.setLocation((int) startPosition.getX() + direction.getXOffset(), (int) startPosition.getY() + direction.getYOffset());
		return startPosition;
	}
}
