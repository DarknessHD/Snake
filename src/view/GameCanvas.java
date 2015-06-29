package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import model.CellObject;
import model.Direction;
import model.Snake;
import model.SnakeSegment;

/**
 * @author Stefan Kameter
 * @version 28.06.2015
 */
public class GameCanvas extends Canvas {
	private static final long serialVersionUID = 1L;

	/**
	 * Component width.
	 */
	public static final int CANVAS_WIDTH = 961;
	/**
	 * Component height.
	 */
	public static final int CANVAS_HEIGHT = 641;
	private static final int TILE_SIZE = 32;
	private static final int TILE_SIZE_BW = (int) (Math.log(TILE_SIZE) / Math
			.log(2));
	/**
	 * The width of tiles in the level.
	 */
	public static final int LEVEL_WIDTH = 30;
	/**
	 * The height of tiles in the level.
	 */
	public static final int LEVEL_HEIGHT = 20;

	private BufferedImage buffer;
	private Graphics bufferGraphics;

	private Snake snake;
	private List<CellObject> cellObjects;

	/**
	 * Creates a new GameCanvas instance.
	 * 
	 * @param snake
	 *            the snake
	 * @param cellObjects
	 *            the list of default CellObjects
	 */
	public GameCanvas(Snake snake, List<CellObject> cellObjects) {
		this.cellObjects = cellObjects;
		this.snake = snake;
		this.snake = new Snake(3, new Point(0, 2), Direction.DOWN); // TODO

		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		initListener();
	}

	private void initListener() {
	}

	/**
	 * @return player snake
	 */
	public Snake getSnake() {
		return snake;
	}

	/**
	 * Adds a CellObject.
	 * 
	 * @param cellObject
	 *            the CellObject
	 */
	public void addCellObject(CellObject cellObject) {
		cellObjects.add(cellObject);
	}

	@Override
	public void paint(Graphics g) {
		if (bufferGraphics == null) {
			buffer = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT,
					BufferedImage.TYPE_INT_ARGB);
			bufferGraphics = buffer.getGraphics();
		}

		bufferGraphics.setColor(getBackground());
		bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

		bufferGraphics.setColor(Color.BLACK);

		for (int y = 0; y < LEVEL_HEIGHT; y++)
			for (int x = 0; x < LEVEL_WIDTH; x++)
				bufferGraphics.drawRect(x << TILE_SIZE_BW, y << TILE_SIZE_BW,
						TILE_SIZE, TILE_SIZE);

		// CellObjects
		for (CellObject o : cellObjects) {
			Point p = o.getPosition();
			bufferGraphics.drawImage(o.getImage(), p.x << TILE_SIZE_BW,
					p.y << TILE_SIZE_BW, TILE_SIZE, TILE_SIZE, null);
		}

		// Snake
		for (SnakeSegment s : snake.getSegments()) {
			Point p = s.getPosition();
			bufferGraphics.drawImage(s.getImage(), p.x << TILE_SIZE_BW,
					p.y << TILE_SIZE_BW, TILE_SIZE, TILE_SIZE, null);
		}

		g.drawImage(buffer, 5, 5, buffer.getWidth(), buffer.getHeight(), null);
	}

	/**
	 * Rotates an image by 90 degrees angle.
	 */
	public static final int DEGREES90 = 1;
	/**
	 * Rotates an image by 180 degrees angle.
	 */
	public static final int DEGREES180 = 2;
	/**
	 * Rotates an image by 270 degrees angle.
	 */
	public static final int DEGREES270 = 3;
	/**
	 * Mirrors an image vertically.
	 */
	public static final int VERTICAL = 4;
	/**
	 * Mirrors an image horizontally.
	 */
	public static final int HORIZONTAL = 5;
	/**
	 * Mirrors an image diagonally.
	 */
	public static final int DIAGONAL = 6;

	/**
	 * Shifts a BufferedImage.
	 * 
	 * @param src
	 *            the source Image
	 * @param shift
	 *            type for shift
	 * @return rotated Image
	 */
	public static BufferedImage shiftImage(BufferedImage src, int shift) {
		if (shift < 1 || shift > 6)
			return src;

		int size = src.getWidth();

		BufferedImage rotatedImage = new BufferedImage(size, size,
				BufferedImage.TYPE_INT_ARGB);

		int[] srcPixels = new int[size * size];
		src.getRGB(0, 0, size, size, srcPixels, 0, size);

		switch (shift) {
		case VERTICAL:
			for (int y = 0; y < size; y++)
				for (int x = 0; x < size; x++)
					rotatedImage.setRGB(x, y,
							srcPixels[size - 1 - x + y * size]);
			break;
		case HORIZONTAL:
			for (int y = 0; y < size; y++)
				for (int x = 0; x < size; x++)
					rotatedImage.setRGB(x, y, srcPixels[x + (size - 1 - y)
							* size]);
			break;
		case DIAGONAL:
			for (int y = 0; y < size; y++)
				for (int x = 0; x < size; x++)
					rotatedImage.setRGB(x, y, srcPixels[size - 1 - x
							+ (size - 1 - y) * size]);
			break;
		default:
			rotateClockWise(srcPixels, rotatedImage, size);
			for (int i = 0; i < shift - 1; i++) {
				int[] pxs = new int[size * size];
				for (int y = 0; y < size; y++)
					for (int x = 0; x < size; x++)
						pxs[x + y * size] = rotatedImage.getRGB(x, y);
				rotateClockWise(pxs, rotatedImage, size);
			}
			break;
		}
		return rotatedImage;
	}

	private static void rotateClockWise(int[] srcPixels,
			BufferedImage rotatedImage, int size) {
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				rotatedImage.setRGB(x, y, srcPixels[y + (size - 1 - x) * size]);
	}
}