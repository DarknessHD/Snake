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

		segments.addFirst(new SnakeSegment(RotatedImage.get(startDirection, HEAD_IMAGE), new Point(startPosition),
				startDirection));

		Direction opposite = startDirection.getOpposite();
		for (int i = 1; i < startSegments - 1; i++)
			segments.add(new SnakeSegment(RotatedImage.get(startDirection, BODY_IMAGE),
					getNextPosition(startPosition, opposite), startDirection));
		segments.addLast(new SnakeSegment(RotatedImage.get(opposite, TAIL_IMAGE), getNextPosition(startPosition, opposite),
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
	 * IMPORTANT: Always use this method to change the direction of the snake's head!
	 * Sets the direction the snake's head is looking into.
	 * 
	 * @param direction
	 *            of the snake's head
	 */
	public void setLookingDirection(Direction direction) {
		SnakeSegment head = segments.getFirst();
		Direction currentDirection = head.getDirection();
		if (direction != currentDirection && direction != currentDirection.getOpposite()) {
			this.lastDirection = head.getDirection();
			head.setImage(RotatedImage.get(direction, HEAD_IMAGE));
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
		segments.getLast().setImage(RotatedImage.get(segments.getLast().getDirection(), TAIL_IMAGE));
	}

	/**
	 * Adds a segment to the snake.
	 */
	public void addSegment() {
		SnakeSegment newBody = segments.getLast();
		newBody.setImage(RotatedImage.get(newBody.getDirection(), BODY_IMAGE));
		segments.addLast(new SnakeSegment(RotatedImage.get(segments.getLast().getDirection(), TAIL_IMAGE), getNextPosition(
				newBody.getPosition(), newBody.getDirection().getOpposite()), newBody.getDirection()));
	}

	/**
	 * Moves the snake one cell forward into the direction its looking into.
	 */
	public void move() {
		SnakeSegment newBody = segments.getFirst();

		if (directionChange) {
			newBody.setImage(RotatedImage.getCurve(lastDirection, getLookingDirection()));
			directionChange = false;
		} else
			newBody.setImage(RotatedImage.get(newBody.getDirection(), BODY_IMAGE));

		segments.addFirst(new SnakeSegment(RotatedImage.get(newBody.getDirection(), HEAD_IMAGE), getNextPosition(new Point(
				newBody.getPosition()), newBody.getDirection()), newBody.getDirection()));

		segments.removeLast();
		segments.getLast().setImage(RotatedImage.get(segments.getLast().getDirection().getOpposite(), TAIL_IMAGE));
	}

	private static Point getNextPosition(Point startPosition, Direction direction) {
		startPosition.setLocation((int) startPosition.getX() + direction.getXOffset(), (int) startPosition.getY()
				+ direction.getYOffset());

		return new Point(startPosition);
	}

	/**
	 * @author Eric Armbruster
	 * @version 25.06.15
	 */
	private static class RotatedImage {

		private static final Direction DEFAULT_INITIAL_DIRECTION = Direction.RIGHT;

		private static String getCurve(Direction lastDirection, Direction newDirection) {
			String curveImage = CURVE_IMAGE + "_" + lastDirection.toString() + "_" + newDirection.toString();

			if (!ImageHolder.isLoaded(curveImage)) {
				BufferedImage curve = ImageHolder.getImage(CURVE_IMAGE);

				if(lastDirection == Direction.DOWN && newDirection == Direction.RIGHT || lastDirection == Direction.LEFT && newDirection == Direction.UP)
					curve = GameCanvas.shiftImage(curve, GameCanvas.DEGREES90);
				else if(lastDirection == Direction.UP && newDirection == Direction.RIGHT || lastDirection == Direction.LEFT && newDirection == Direction.DOWN)
					curve = GameCanvas.shiftImage(curve, GameCanvas.DEGREES180);
				else if(lastDirection == Direction.UP && newDirection == Direction.LEFT || lastDirection == Direction.RIGHT && newDirection == Direction.DOWN)
					curve = GameCanvas.shiftImage(curve, GameCanvas.DEGREES270);
				
				ImageHolder.putImage(curveImage, curve);
			}

			return curveImage;
		}
		
		private static String get(Direction newDirection, String image) {
			return get(newDirection, DEFAULT_INITIAL_DIRECTION, image);
		}

		private static String get(Direction newDirection, Direction initialDirection, String image) {
			String rotatedImage = image + "_" + newDirection.toString();
			
			if (!ImageHolder.isLoaded(rotatedImage)) {	
				BufferedImage bufferedImage = ImageHolder.getImage(image);
				if(initialDirection != newDirection) {
					for (int i = 1; i < Direction.values().length; i++) {
						if ((initialDirection = initialDirection.getNext()) == newDirection) {

							switch (i) {
							case 1:
								bufferedImage = GameCanvas.shiftImage(bufferedImage, GameCanvas.DEGREES90);
								break;
							case 2:
								bufferedImage = GameCanvas.shiftImage(bufferedImage, GameCanvas.DEGREES180);
								break;
							case 3:
								bufferedImage = GameCanvas.shiftImage(bufferedImage, GameCanvas.DEGREES270);
								break;
							}
						}	
					}	
				}
				ImageHolder.putImage(rotatedImage, bufferedImage);
			}
			return rotatedImage;
		}
	}
}