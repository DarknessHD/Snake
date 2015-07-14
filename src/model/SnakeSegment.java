package model;

import java.util.Objects;

import view.GameFrame;

/**
 * @author Eric Armbruster
 * @version 23.06.2015
 */
public class SnakeSegment extends CellObject {

	/**
	 * @author Eric Armbruster
	 * @version 14.07.2015
	 */
	public enum SegmentType {
		/**
		 * A head segment of a snake.
		 */
		SNAKE_HEAD,
		/**
		 * A body segment of a snake.
		 */
		SNAKE_BODY,
		/**
		 * A curve segment of a snake.
		 */
		SNAKE_CURVE,
		/**
		 * A tail segment of a snake.
		 */
		SNAKE_TAIL;
	}

	private Direction direction, secondCurveDirection, firstCurveDirection;
	private SegmentType segmentType;

	/**
	 * Creates a new SnakeSegment instance.
	 *
	 * @param position
	 *            the position
	 * @param direction
	 *            the direction
	 * @param segmentType
	 *            the segment type
	 * @param firstCurveDirection
	 *            the first curve direction
	 * @param secondCurveDirection
	 *            the second curve direction
	 */
	public SnakeSegment(TilePosition position, Direction direction, SegmentType segmentType,
			Direction firstCurveDirection, Direction secondCurveDirection) {
		super(null, position);
		this.direction = Objects.requireNonNull(direction);
		setSegmentType(segmentType, firstCurveDirection, secondCurveDirection);
	}

	/**
	 * Creates a new SnakeSegment instance.
	 *
	 * @param position
	 *            the position
	 * @param direction
	 *            the direction
	 * @param segmentType
	 */
	public SnakeSegment(TilePosition position, Direction direction, SegmentType segmentType) {
		this(position, direction, segmentType, null, null);
	}

	/**
	 * Returns the direction.
	 * 
	 * @return the direction of the snake segment
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Sets the direction.
	 * 
	 * @param direction
	 *            the direction of the snake segment
	 */
	public void setDirection(Direction direction) {
		this.direction = Objects.requireNonNull(direction);
	}

	/**
	 * Returns the SegmentType.
	 * 
	 * @return the segment type
	 */
	public SegmentType getSegmentType() {
		return this.segmentType;
	}

	/**
	 * Sets the SegmentType.
	 * 
	 * @param segmentType
	 *            the segment type to set
	 * @param firstCurveDirection
	 *            the first direction that is used, if the segmentType is
	 *            SNAKE_CURVE
	 * @param secondCurveDirection
	 *            the second direction that is used, if the segmentType is
	 *            SNAKE_CURVE
	 */
	public void setSegmentType(SegmentType segmentType, Direction firstCurveDirection, Direction secondCurveDirection) {
		this.segmentType = Objects.requireNonNull(segmentType);

		switch (segmentType) {
		case SNAKE_HEAD:
			super.setImage(RotatedImage.get(direction, RotatedImage.HEAD_IMAGE));
			this.secondCurveDirection = null;
			break;
		case SNAKE_BODY:
			super.setImage(RotatedImage.get(direction, RotatedImage.BODY_IMAGE));
			this.secondCurveDirection = null;
			break;
		case SNAKE_CURVE:
			super.setImage(RotatedImage.getCurve(firstCurveDirection, secondCurveDirection));
			this.secondCurveDirection = secondCurveDirection;
			break;
		case SNAKE_TAIL:
			super.setImage(RotatedImage.get(direction.getOpposite(), RotatedImage.TAIL_IMAGE));
			this.secondCurveDirection = null;
			break;
		}
	}

	/**
	 * Sets the SegmentType.
	 * 
	 * @param segmentType
	 *            the segment type to set
	 */
	public void setSegmentType(SegmentType segmentType) {
		setSegmentType(segmentType, null, null);
	}

	/**
	 * Returns the first curve direction, if the segmentType is SNAKE_CURVE.
	 * 
	 * @return the first curve direction
	 */
	public Direction getFirstCurveDirection() {
		if (segmentType == SegmentType.SNAKE_CURVE)
			return this.firstCurveDirection;

		return null;
	}

	/**
	 * Returns the second curve direction, if the segmentType is SNAKE_CURVE.
	 * 
	 * @return the second curve direction
	 */
	public Direction getSecondCurveDirection() {
		if (segmentType == SegmentType.SNAKE_CURVE)
			return this.secondCurveDirection;

		return null;
	}

	@Override
	public void setImage(String image) {
		throw new UnsupportedOperationException("Use setSegmentType instead!");
	}

	@Override
	public void onSnakeHitCellObject(Snake snake) {
		GameFrame.getInstance().stop();
	}
}