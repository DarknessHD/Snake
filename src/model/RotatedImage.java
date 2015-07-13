package model;

import io.ImageHolder;

import java.awt.image.BufferedImage;

import control.ShiftType;
import view.GamePanel;

/**
 * @author Eric Armbruster
 * @version 25.06.15
 */
public final class RotatedImage {

	/**
	 * The file name of the head image
	 */
	public static final String HEAD_IMAGE = "snake_head";
	/**
	 * The file name of the tail image
	 */
	public static final String TAIL_IMAGE = "snake_tail";
	/**
	 * The file name of the body image
	 */
	public static final String BODY_IMAGE = "snake_body";
	/**
	 * The file name of the curve image
	 */
	public static final String CURVE_IMAGE = "snake_curve";

	private static final Direction DEFAULT_INITIAL_DIRECTION = Direction.RIGHT;

	private RotatedImage() {
	};

	/**
	 * Returns the string under which the BufferedImage is saved in the
	 * ImageHolder.
	 * 
	 * @param lastDirection
	 *            the last direction
	 * @param newDirection
	 *            the new direction
	 * @return saved image string
	 */
	public static String getCurve(Direction lastDirection, Direction newDirection) {
		String curveImage = CURVE_IMAGE + "_" + lastDirection.toString() + "_" + newDirection.toString();

		if (!ImageHolder.isLoaded(curveImage)) {
			BufferedImage curve = ImageHolder.getImage(CURVE_IMAGE);

			if (lastDirection == Direction.DOWN && newDirection == Direction.RIGHT || lastDirection == Direction.LEFT
					&& newDirection == Direction.UP)
				curve = GamePanel.shiftImage(curve, ShiftType.DEGREES90);
			else if (lastDirection == Direction.UP && newDirection == Direction.RIGHT
					|| lastDirection == Direction.LEFT && newDirection == Direction.DOWN)
				curve = GamePanel.shiftImage(curve, ShiftType.DEGREES180);
			else if (lastDirection == Direction.UP && newDirection == Direction.LEFT
					|| lastDirection == Direction.RIGHT && newDirection == Direction.DOWN)
				curve = GamePanel.shiftImage(curve, ShiftType.DEGREES270);

			ImageHolder.putImage(curveImage, curve);
		}

		return curveImage;
	}

	/**
	 * Returns the string under which the rotated BufferedImage is saved in the
	 * ImageHolder.
	 * 
	 * @param newDirection the new rotation
	 * @param image the image
	 * @return saved image string
	 */
	public static String get(Direction newDirection, String image) {
		return get(newDirection, DEFAULT_INITIAL_DIRECTION, image);
	}

	/**
	 * Returns the string under which the rotated BufferedImage is saved in the
	 * ImageHolder.
	 * 
	 * @param newDirection the new rotation
	 * @param initialDirection the initial rotation
	 * @param image the image
	 * @return saved image string
	 */
	public static String get(Direction newDirection, Direction initialDirection, String image) {
		String rotatedImage = image + "_" + newDirection.toString();

		if (!ImageHolder.isLoaded(rotatedImage)) {
			BufferedImage bufferedImage = ImageHolder.getImage(image);
			if (initialDirection != newDirection) {
				for (int i = 1; i < Direction.values().length; i++) {
					if ((initialDirection = initialDirection.getNext()) == newDirection) {

						switch (i) {
						case 1:
							bufferedImage = GamePanel.shiftImage(bufferedImage, ShiftType.DEGREES90);
							break;
						case 2:
							bufferedImage = GamePanel.shiftImage(bufferedImage, ShiftType.DEGREES180);
							break;
						case 3:
							bufferedImage = GamePanel.shiftImage(bufferedImage, ShiftType.DEGREES270);
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