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
	private static final String HEAD_IMAGE = "snake_head", TAIL_IMAGE = "snake_tail", BODY_IMAGE = "snake_body",
			CURVE_IMAGE = "snake_curve";

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

		segments.addFirst(new SnakeSegment(RotatedImage.getHead(startDirection), new Point(startPosition),
				startDirection));

		Direction opposite = startDirection.getOpposite();
		for (int i = 1; i < startSegments - 1; i++)
			segments.add(new SnakeSegment(BODY_IMAGE, new Point(getNextPosition(startPosition, opposite)),
					startDirection));
		segments.addLast(new SnakeSegment(RotatedImage.getTail(opposite), new Point(getNextPosition(startPosition, opposite)),
				startDirection));
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
	 * Sets the direction the snake's head is looking into.
	 * 
	 * @param direction
	 *            of the snake's head
	 */
	public void setLookingDirection(Direction direction) {
		SnakeSegment head = segments.getFirst();
		String headImage = RotatedImage.getHead(direction);
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
		segments.getLast().setImage(RotatedImage.getTail(segments.getLast().getDirection()));
	}

	/**
	 * Adds a segment to the snake.
	 */
	public void addSegment() {
		SnakeSegment tail = segments.getLast();
		tail.setImage(BODY_IMAGE);
		segments.addLast(new SnakeSegment(RotatedImage.getTail(segments.getLast().getDirection()), getNextPosition(
				tail.getPosition(), tail.getDirection().getOpposite()), tail.getDirection())); //TODO New Tail
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

			head.setImage(RotatedImage.getCurve(lastDirection, getLookingDirection()));
		} else
			head.setImage(BODY_IMAGE);
		segments.addFirst(new SnakeSegment(RotatedImage.getHead(head.getDirection()), getNextPosition(
				head.getPosition(), head.getDirection()), head.getDirection()));

		removeSegment();
	}

	private static Point getNextPosition(Point startPosition, Direction direction) {
		startPosition.setLocation((int) startPosition.getX() + direction.getXOffset(), (int) startPosition.getY()
				+ direction.getYOffset());
		return startPosition;
	}

	private static class RotatedImage {

		private static String getCurve(Direction lastDirection, Direction newDirection) {
			String curveImage = CURVE_IMAGE + "_" + lastDirection.toString() + "_" + newDirection.toString();

			if (!ImageHolder.isLoaded(curveImage)) {
				BufferedImage curve = ImageHolder.getImage(CURVE_IMAGE);

				if (lastDirection == Direction.DOWN || newDirection == Direction.DOWN
						&& lastDirection == Direction.LEFT || newDirection == Direction.LEFT)
					curve = GameCanvas.mirrorImage(curve, GameCanvas.HORIZONTAL);
				else if (lastDirection == Direction.DOWN || newDirection == Direction.DOWN
						&& lastDirection == Direction.RIGHT || newDirection == Direction.RIGHT)
					curve = GameCanvas.mirrorImage(curve, GameCanvas.DIAGONAL);
				else if (lastDirection == Direction.UP || newDirection == Direction.UP
						&& lastDirection == Direction.RIGHT || newDirection == Direction.RIGHT)
					curve = GameCanvas.mirrorImage(curve, GameCanvas.VERTICAL);
				ImageHolder.putImage(curveImage, curve);
			}

			return curveImage;
		}

		private static String getHead(Direction headDirection) {
			String headImage = HEAD_IMAGE + "_" + headDirection.toString();

			if (!ImageHolder.isLoaded(headImage)) {
				BufferedImage head = ImageHolder.getImage(HEAD_IMAGE);

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

		private static String getTail(Direction tailDirection) {
			String tailImage = TAIL_IMAGE + "_" + tailDirection.toString();

			if (!ImageHolder.isLoaded(tailImage)) {
				BufferedImage tail = ImageHolder.getImage(TAIL_IMAGE);

				switch (tailDirection) {
				case UP:
					tail = GameCanvas.mirrorImage(tail, GameCanvas.VERTICAL);
					break;
				case DOWN:
					tail = GameCanvas.mirrorImage(tail, GameCanvas.HORIZONTAL);
					break;
				case LEFT:
					break;
				case RIGHT:
					tail = GameCanvas.mirrorImage(tail, GameCanvas.DIAGONAL);
					break;
				}
				ImageHolder.putImage(tailImage, tail);
			}

			return tailImage;
		}
	}
}