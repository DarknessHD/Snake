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
import model.Item;
import model.ItemSpawner;
import model.Snake;
import model.cellobject.SnakeSegment;

/**
 * @author Stefan Kameter
 * @version 02.07.2015
 */
public class GameCanvas extends Canvas {
	private static final long serialVersionUID = 1L;

	private static final int TILE_SIZE = 32;
	private static final int TILE_SIZE_BW = (int) (Math.log(TILE_SIZE) / Math.log(2));
	/**
	 * The width of tiles in the level.
	 */
	public static final int LEVEL_WIDTH = 30;
	/**
	 * The height of tiles in the level.
	 */
	public static final int LEVEL_HEIGHT = 20;
	/**
	 * Component width.
	 */
	public static final int CANVAS_WIDTH = LEVEL_WIDTH * TILE_SIZE + 1;
	/**
	 * Component height.
	 */
	public static final int CANVAS_HEIGHT = LEVEL_HEIGHT * TILE_SIZE + 1;

	private BufferedImage buffer;
	private Graphics bufferGraphics;

	private boolean initialized;

	private Snake[] snakes;
	private List<CellObject> staticObjects;
	private List<Item> items;

	/**
	 * Creates a new GameCanvas instance.
	 */
	public GameCanvas() {
		initialized = false;

		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		initListener();
	}

	private void initListener() {
	}

	/**
	 * Sets the Level.
	 * 
	 * @param snakes
	 *            the snakes
	 * @param staticObjects
	 *            the static CellObjects
	 * @param items
	 *            the list of default Items
	 */
	public void setLevel(Snake[] snakes, List<CellObject> staticObjects, List<Item> items) {
		this.snakes = snakes;
		this.staticObjects = staticObjects;
		this.items = items;

		this.items = new ArrayList<Item>(); // TODO
		this.staticObjects = new ArrayList<CellObject>(); // TODO
		this.snakes = new Snake[2]; // TODO
		this.snakes[0] = new Snake(3, new Point(4, 5), Direction.DOWN); // TODO
		this.snakes[1] = new Snake(3, new Point(20, 5), Direction.DOWN); // TODO
		this.snakes[1].setPathfinder();

		initialized = true;
	}

	/**
	 * Returns the snakes.
	 * 
	 * @return snakes
	 */
	public Snake[] getSnakes() {
		return snakes;
	}

	/**
	 * Adds an Item.
	 * 
	 * @param item
	 *            the Item
	 */
	public void addItem(Item item) {
		items.add(item);
	}

	/**
	 * Executes onSnakeHitCellObject, if necessary.
	 * 
	 * @param index
	 *            the desired snake
	 */
	public void onMove(int index) {
		CellObject head = snakes[index].getHead();
		Point sp = head.getPosition();
		List<SnakeSegment> segments = new ArrayList<SnakeSegment>(snakes[index].getSegments());

		for (int i = 0; i < segments.size(); i++) {
			SnakeSegment seg = segments.get(i);
			if (seg.equals(head))
				continue;
			if (sp.equals(seg.getPosition())) {
				seg.onSnakeHitCellObject(snakes[index]);
				segments.remove(i);
			}
		}

		for (int i = 0; i < staticObjects.size(); i++) {
			CellObject obj = staticObjects.get(i);
			if (sp.equals(obj.getPosition()))
				obj.onSnakeHitCellObject(snakes[index]);
		}

		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (sp.equals(item.getPosition())) {
				item.onSnakeHitCellObject(snakes[index]);
				items.remove(i);
				items.add(ItemSpawner.getRandomItem());
			}
		}
	}

	/**
	 * Checks whether an item is already placed at that position.
	 * 
	 * @param position
	 *            the position to check
	 * @return whether an item is already placed at that position
	 */
	public boolean checkPosition(Point position) {
		if (items != null)
			for (Item i : items)
				if (i.getPosition().equals(position))
					return false;

		if (staticObjects != null)
			for (CellObject obj : staticObjects)
				if (obj.getPosition().equals(position))
					return false;

		if (snakes != null)
			for (Snake snake : snakes)
				for (SnakeSegment s : snake.getSegments())
					if (s.getPosition().equals(position))
						return false;

		return true;
	}

	/**
	 * Returns all Items.
	 * 
	 * @return all items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * Returns all Items.
	 * 
	 * @return all items
	 */
	public List<CellObject> getStaticObjects() {
		return staticObjects;
	}

	@Override
	public void paint(Graphics g) {
		if (initialized) {
			if (bufferGraphics == null) {
				buffer = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
				bufferGraphics = buffer.getGraphics();
			}

			bufferGraphics.setColor(getBackground());
			bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

			bufferGraphics.setColor(Color.WHITE);

			for (int y = 0; y < LEVEL_HEIGHT; y++)
				for (int x = 0; x < LEVEL_WIDTH; x++)
					bufferGraphics.drawRect(x << TILE_SIZE_BW, y << TILE_SIZE_BW, TILE_SIZE, TILE_SIZE);

			// Items
			for (Item i : items) {
				Point p = i.getPosition();
				bufferGraphics.drawImage(i.getImage(), p.x << TILE_SIZE_BW, p.y << TILE_SIZE_BW, TILE_SIZE, TILE_SIZE, null);
			}

			// Snakes
			for (Snake snake : snakes)
				for (SnakeSegment s : snake.getSegments()) {
					Point p = s.getPosition();
					bufferGraphics.drawImage(s.getImage(), p.x << TILE_SIZE_BW, p.y << TILE_SIZE_BW, TILE_SIZE, TILE_SIZE, null);
				}

			// StaticObjects
			for (CellObject obj : staticObjects) {
				Point p = obj.getPosition();
				bufferGraphics.drawImage(obj.getImage(), p.x << TILE_SIZE_BW, p.y << TILE_SIZE_BW, TILE_SIZE, TILE_SIZE, null);
			}

			g.drawImage(buffer, 5, 5, buffer.getWidth(), buffer.getHeight(), null);
		}
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

		BufferedImage rotatedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

		int[] srcPixels = new int[size * size];
		src.getRGB(0, 0, size, size, srcPixels, 0, size);

		switch (shift) {
		case VERTICAL:
			for (int y = 0; y < size; y++)
				for (int x = 0; x < size; x++)
					rotatedImage.setRGB(x, y, srcPixels[size - 1 - x + y * size]);
			break;
		case HORIZONTAL:
			for (int y = 0; y < size; y++)
				for (int x = 0; x < size; x++)
					rotatedImage.setRGB(x, y, srcPixels[x + (size - 1 - y) * size]);
			break;
		case DIAGONAL:
			for (int y = 0; y < size; y++)
				for (int x = 0; x < size; x++)
					rotatedImage.setRGB(x, y, srcPixels[size - 1 - x + (size - 1 - y) * size]);
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

	private static void rotateClockWise(int[] srcPixels, BufferedImage rotatedImage, int size) {
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				rotatedImage.setRGB(x, y, srcPixels[y + (size - 1 - x) * size]);
	}
}