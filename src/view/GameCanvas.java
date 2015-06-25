package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import model.CellObject;
import model.Direction;
import model.Snake;
import model.SnakeSegment;

/**
 * @author Stefan Kameter
 * @version 22.06.2015
 */
public class GameCanvas extends Canvas {
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 960;
	private static final int HEIGHT = 640;
	private static final int TILE_WIDTH = 60;
	private static final int TILE_HEIGHT = 40;
	private static final int TILE_SIZE = 16;

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
		this.cellObjects = new LinkedList<CellObject>(); // TODO
		snake = new Snake(5, new Point(5, 5), Direction.LEFT); // TODO

		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		initListener();
	}

	private void initListener() {
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
			buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
			bufferGraphics = buffer.getGraphics();
		}

		bufferGraphics.setColor(Color.WHITE);

		for (int y = 0; y < TILE_HEIGHT; y++)
			for (int x = 0; x < TILE_WIDTH; x++)
				bufferGraphics.drawRect(5 + x * TILE_SIZE, 5 + y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

		// CellObjects
		for (CellObject d : cellObjects) {
			Point position = d.getPosition();
			bufferGraphics.drawImage(d.getImage(), 5 + position.x * TILE_SIZE, 5 + position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
		}

		// Snake
		for (SnakeSegment s : snake.getSegments()) {
			Point p = s.getPosition();
			g.drawImage(s.getImage(), 5 + p.x * TILE_SIZE, 5 + p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
		}

		g.drawImage(buffer, 0, 0, WIDTH, HEIGHT, null);
	}

	/**
	 * Mirrors an image vertically.
	 */
	public static final int VERTICAL = 0;
	/**
	 * Mirrors an image diagonally.
	 */
	public static final int DIAGONAL = 1;
	/**
	 * Mirrors an image horizontally.
	 */
	public static final int HORIZONTAL = 2;

	/**
	 * Shifts a BufferedImage.
	 * 
	 * @param src
	 *            the source Image
	 * @param shift
	 *            types for shift
	 * @return rotated Image
	 */
	public static BufferedImage mirrorImage(BufferedImage src, int shift) {
		if (shift < 0 || shift > 2)
			return src;

		int width = src.getWidth();
		int height = src.getHeight();

		BufferedImage rotatedImage = new BufferedImage(width, height, src.getType());

		int[] srcPixels = new int[width * height];
		src.getRGB(0, 0, width, height, srcPixels, 0, width);

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				switch (shift) {
				case VERTICAL:
					rotatedImage.setRGB(x, y, srcPixels[width - 1 - x + y * width]);
					break;
				case DIAGONAL:
					rotatedImage.setRGB(x, y, srcPixels[width - 1 - x + (height - 1 - y) * width]);
					break;
				case HORIZONTAL:
					rotatedImage.setRGB(x, y, srcPixels[x + (height - 1 - y) * width]);
					break;
				}

		return rotatedImage;
	}
}