package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.CellObject;
import model.Direction;
import model.Item;
import model.ItemSpawner;
import model.Snake;
import model.cellobject.SnakeSegment;
import model.cellobject.Wall;
import control.Comp;
import control.ShiftType;

/**
 * @author Stefan Kameter
 * @version 02.07.2015
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * The pixel size of a tile.
	 */
	public static final int TILE_SIZE = 32;
	/**
	 * The size of a tile (BW).
	 */
	public static final int TILE_SIZE_BW = (int) (Math.log(TILE_SIZE) / Math.log(2));
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
	private boolean paused;
	private boolean gameOver;

	private String level;

	private Snake[] snakes;
	private List<CellObject> staticObjects;
	private List<Item> items;

	/**
	 * Creates a new GamePanel instance.
	 */
	public GamePanel() {
		initialized = false;
		paused = false;
		gameOver = false;

		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		initListener();
	}

	private void initListener() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (gameOver) {
					if (snakes.length == 1)
						GameFrame.getInstance().addToScoreList(level, snakes[0].getScore());
					GameFrame.getInstance().changeComponent(Comp.GAMEMENUPANEL);
				} else if (paused)
					setPaused(false);
			}
		});
	}

	/**
	 * Sets the Level.
	 * 
	 * @param level
	 *            the level name
	 * @param snakes
	 *            the snakes
	 * @param staticObjects
	 *            the static CellObjects
	 * @param items
	 *            the list of default Items
	 */
	public void setLevel(String level, Snake[] snakes, List<CellObject> staticObjects, List<Item> items) {
		this.level = level;
		this.snakes = snakes;
		this.staticObjects = staticObjects;
		this.items = items;

		// TODO
		this.items = new ArrayList<Item>();
		this.staticObjects = new ArrayList<CellObject>();
		for (int i = 0; i < LEVEL_HEIGHT; i++) {
			if (i > 5 && i < 8)
				continue;
			this.staticObjects.add(new Wall(new Point(0, i)));
			this.staticObjects.add(new Wall(new Point(LEVEL_WIDTH - 1, i)));
		}
		for (int i = 0; i < LEVEL_WIDTH; i++) {
			this.staticObjects.add(new Wall(new Point(i, 0)));
			this.staticObjects.add(new Wall(new Point(i, LEVEL_HEIGHT - 1)));
		}
		this.snakes = new Snake[1];
		this.snakes[0] = new Snake(3, new Point(4, 5), Direction.DOWN);
		// this.snakes[1] = new Snake(3, new Point(20, 5), Direction.DOWN);
		// this.snakes[1].setPathfinder();
		// TODO

		initialized = true;
		paused = false;
		gameOver = false;
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

	/**
	 * Adds an Item.
	 * 
	 * @param item
	 *            the Item
	 */
	public void addItem(Item item) {
		items.add(item);
		// TODO repainting
		// Point p = item.getPosition();
		// repaint(new Rectangle(p.x << TILE_SIZE_BW + 5, p.y << TILE_SIZE_BW + 5, TILE_SIZE, TILE_SIZE));
	}

	/**
	 * Sets whether game is over.
	 * 
	 * @param gameOver
	 *            whether game is over
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Sets whether game is paused.
	 * 
	 * @param paused
	 *            whether game is paused
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
		if (this.paused)
			repaint();
	}

	/**
	 * @return whether game is paused
	 */
	public boolean isPaused() {
		return paused;
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

	@Override
	public void paint(Graphics g) {
		if (initialized) {
			if (bufferGraphics == null) {
				buffer = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
				bufferGraphics = buffer.getGraphics();
			}

			if (paused)
				drawPaused();
			else if (gameOver)
				drawGameOver();
			else
				drawGame();

			g.drawImage(buffer, 5, 5, buffer.getWidth(), buffer.getHeight(), null);
		}
	}

	private void drawGame() {
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
	}

	private void drawPaused() {
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.setFont(new Font("SanSarif", Font.BOLD, 60));
		String string = "Game Paused";
		int width = bufferGraphics.getFontMetrics(bufferGraphics.getFont()).stringWidth(string);
		bufferGraphics.drawString(string, (CANVAS_WIDTH - width) / 2, CANVAS_HEIGHT / 4);

		drawScore();

		drawCTC();
	}

	private void drawGameOver() {
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.setFont(new Font("SanSarif", Font.BOLD, 60));
		String string = "Game Over";
		int width = bufferGraphics.getFontMetrics(bufferGraphics.getFont()).stringWidth(string);
		bufferGraphics.drawString(string, (CANVAS_WIDTH - width) / 2, CANVAS_HEIGHT / 4);

		drawWinning();

		drawScore();

		drawCTC();
	}

	private void drawScore() {
		switch (snakes.length) {
		case 1:
			drawScoreSP();
			break;
		case 2:
			drawScoreMP();
			break;
		}
	}

	private void drawScoreSP() {
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.setFont(new Font("SanSarif", Font.BOLD, 45));
		String string = "Score: " + snakes[0].getScore();
		int width = bufferGraphics.getFontMetrics(bufferGraphics.getFont()).stringWidth(string);
		bufferGraphics.drawString(string, (CANVAS_WIDTH - width) / 2, CANVAS_HEIGHT / 2);
	}

	private void drawScoreMP() {
		bufferGraphics.setColor(Color.BLACK);

		for (int i = 0; i < snakes.length; i++) {
			bufferGraphics.setFont(new Font("SanSarif", Font.BOLD, 45));
			String string = "(Snake" + i + ") Score: " + snakes[i].getScore();
			int width = bufferGraphics.getFontMetrics(bufferGraphics.getFont()).stringWidth(string);
			bufferGraphics.drawString(string, (CANVAS_WIDTH - width) / 2, CANVAS_HEIGHT / 2 + i * 80);
		}
	}

	private void drawWinning() {
		// TODO whether you have won or lost
		bufferGraphics.setColor(Color.ORANGE);
	}

	private void drawCTC() {
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.setFont(new Font("SanSarif", Font.BOLD, 20));
		String string = "Click to continue";
		int width = bufferGraphics.getFontMetrics(bufferGraphics.getFont()).stringWidth(string);
		bufferGraphics.drawString(string, (CANVAS_WIDTH - width) / 2, CANVAS_HEIGHT - 50);
	}

	/**
	 * Shifts a BufferedImage.
	 * 
	 * @param src
	 *            the source Image
	 * @param shift
	 *            type for shift
	 * @return rotated Image
	 */
	public static BufferedImage shiftImage(BufferedImage src, ShiftType shift) {
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
			for (int i = 0; i < shift.getNumber() - 1; i++) {
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