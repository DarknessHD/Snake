package control;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public enum ShiftType {
	/**
	 * Rotates an image by 90 degrees angle.
	 */
	DEGREES90,
	/**
	 * Rotates an image by 180 degrees angle.
	 */
	DEGREES180,
	/**
	 * Rotates an image by 270 degrees angle.
	 */
	DEGREES270,
	/**
	 * Mirrors an image vertically.
	 */
	VERTICAL,
	/**
	 * Mirrors an image horizontally.
	 */
	HORIZONTAL,
	/**
	 * Mirrors an image diagonally.
	 */
	DIAGONAL;

	/**
	 * @return a number
	 */
	public int getNumber() {
		switch (this) {
		case DEGREES90:
			return 1;
		case DEGREES180:
			return 2;
		case DEGREES270:
			return 3;
		default:
			return 0;
		}
	}
}