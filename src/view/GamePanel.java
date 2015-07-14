package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.swing.JPanel;

import model.CellObject;
import model.Level;
import model.Snake;
import model.SnakeSegment;
import model.StaticCellObject;
import model.item.Item;
import control.Comp;
import control.Constants;
import control.ItemSpawner;
import control.ShiftType;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final Color COLOR = new Color(238, 238, 238);

	private static final String STR_ERROR = "ConcurrentModificationException!";

	private static final String STR_PAUSED = "Game Paused";
	private static final String STR_GAMEOVER = "Game Over";
	private static final String STR_SCORESP = "Score: ";
	private static final String STR_SCOREMP = "(Snake <index>) Score: ";
	private static final String STR_CTC = "Click To Continue";

	private Level level;

	private BufferedImage buffer;
	private Graphics bufferGraphics;

	private boolean first;

	private boolean paused;
	private boolean gameOver;

	/**
	 * Creates a new GamePanel instance.
	 */
	public GamePanel() {
		first = true;

		paused = false;
		gameOver = false;

		setPreferredSize(new Dimension(Constants.CONTENT_WIDTH, Constants.CONTENT_HEIGHT));

		initListener();
	}

	private void initListener() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (gameOver) {
					if (level.snakes.length == 1)
						GameFrame.getInstance().addToScoreList(level.name, level.snakes[0].getScore());
					GameFrame.getInstance().changeComponent(Comp.GAMEMENUPANEL);
				} else if (paused) {
					setPaused(false);
				}
			}
		});
	}

	/**
	 * Sets the Level.
	 * 
	 * @param level
	 *            the new Level
	 */
	public void setLevel(Level level) {
		this.level = level;

		first = true;

		paused = false;
		gameOver = false;
	}

	/**
	 * Returns the Level.
	 * 
	 * @return the Level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Sets whether game is over.
	 * 
	 * @param gameOver
	 *            whether game is over
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
		repaint();
	}

	/**
	 * Sets whether game is paused.
	 * 
	 * @param paused
	 *            whether game is paused
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;

		if (!this.paused)
			first = true;

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
		CellObject head = level.snakes[index].getHead();
		Point hp = head.getPosition();

		for (SnakeSegment seg : level.snakes[index].getSegments()) {
			if (seg.equals(head))
				continue;
			if (hp.equals(seg.getPosition()))
				seg.onSnakeHitCellObject(level.snakes[index]);
		}

		if (level.snakes.length != 1)
			for (int i = 0; i < level.snakes.length; i++)
				if (i != index)
					for (SnakeSegment seg : level.snakes[i].getSegments()) {
						if (seg.equals(head))
							continue;
						if (hp.equals(seg.getPosition()))
							seg.onSnakeHitCellObject(level.snakes[index]);
					}

		for (int i = 0; i < level.staticCellObjects.length; i++) {
			CellObject obj = level.staticCellObjects[i];
			if (hp.equals(obj.getPosition()))
				obj.onSnakeHitCellObject(level.snakes[index]);
		}

		List<Item> items = level.getItems();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (hp.equals(item.getPosition())) {
				item.onSnakeHitCellObject(level.snakes[index]);
				items.remove(i);
				level.addItem(ItemSpawner.getRandomItem());
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
		if (level != null) {
			for (Item i : level.getItems())
				if (i.getPosition().equals(position))
					return false;

			if (level.staticCellObjects != null)
				for (CellObject obj : level.staticCellObjects)
					if (obj.getPosition().equals(position))
						return false;

			if (level.snakes != null)
				for (Snake snake : level.snakes)
					for (SnakeSegment s : snake.getSegments())
						if (s.getPosition().equals(position))
							return false;
		}

		return true;
	}

	/**
	 * Repaints only one tile.
	 * 
	 * @param position
	 *            the tile position
	 */
	public void doRepaint(Point position) {
		if (bufferGraphics == null)
			return;

		List<CellObject> objs = new ArrayList<CellObject>();

		back: for (Snake s : level.snakes)
			for (SnakeSegment seg : s.getSegments())
				if (seg.getPosition().equals(position)) {
					objs.add(seg);
					break back;
				}
		for (StaticCellObject sco : level.staticCellObjects)
			if (sco.getPosition().equals(position)) {
				objs.add(sco);
				break;
			}
		for (Item i : level.getItems())
			if (i.getPosition().equals(position)) {
				objs.add(i);
				break;
			}
		bufferGraphics.setColor(COLOR);
		bufferGraphics.fillRect(position.x << Constants.TILE_SIZE_BW, position.y << Constants.TILE_SIZE_BW, Constants.TILE_SIZE, Constants.TILE_SIZE);

		drawCellObjects(objs);

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		if (level != null) {
			if (bufferGraphics == null) {
				buffer = new BufferedImage(Constants.CONTENT_WIDTH, Constants.CONTENT_HEIGHT, BufferedImage.TYPE_INT_ARGB);
				bufferGraphics = buffer.getGraphics();
			}

			if (paused)
				drawPaused();
			else if (gameOver)
				drawGameOver();
			else
				drawScore2(); // only two snakes

			if (first)
				drawGame();

			g.drawImage(buffer, 5, 5, buffer.getWidth(), buffer.getHeight(), null);
		}
	}

	private void drawScore2() {
		fL();

		bufferGraphics.setColor(Color.YELLOW);

		for (int i = 0; i < level.snakes.length; i++) {
			if (i == 2)
				return;

			String score = STR_SCOREMP.replace("<index>", i + "") + level.snakes[i].getScore();
			int x = 10;
			if (i != 0)
				x = Constants.CONTENT_WIDTH - getFontWidth(score, 20) - 10;
			bufferGraphics.drawString(score, x, getFontHeight(score, 20));
		}
	}

	private void fL() {
		for (int x = 0; x < Constants.LEVEL_WIDTH; x++)
			doRepaint(new Point(x, 0));
	}

	private void drawGame() {
		first = false;

		bufferGraphics.setColor(COLOR);
		bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

		// Items
		drawCellObjects(level.getItems());

		// Snakes
		for (Snake snake : level.snakes)
			drawCellObjects(snake.getSegments());

		// StaticCellObjects
		drawCellObjects(level.staticCellObjects);
	}

	private void drawCellObjects(CellObject[] objects) {
		for (CellObject obj : objects) {
			Point p = obj.getPosition();
			bufferGraphics.drawImage(obj.getImage(), p.x << Constants.TILE_SIZE_BW, p.y << Constants.TILE_SIZE_BW, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
		}
	}

	private void drawCellObjects(Iterable<? extends CellObject> objects) {
		try {
			for (CellObject obj : objects) {
				Point p = obj.getPosition();
				bufferGraphics.drawImage(obj.getImage(), p.x << Constants.TILE_SIZE_BW, p.y << Constants.TILE_SIZE_BW, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
			}
		} catch (ConcurrentModificationException e) {
			System.err.println(STR_ERROR); // TODO
		}
	}

	private void drawPaused() {
		fL();
		
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.drawString(STR_PAUSED, (Constants.CONTENT_WIDTH - getFontWidth(STR_PAUSED, 60)) / 2, Constants.CONTENT_HEIGHT / 4);

		drawScore();

		drawCTC();
	}

	private void drawGameOver() {
		fL();
		
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.drawString(STR_GAMEOVER, (Constants.CONTENT_WIDTH - getFontWidth(STR_GAMEOVER, 60)) / 2, Constants.CONTENT_HEIGHT / 4);

		drawScore();

		drawCTC();
	}

	private void drawScore() {
		switch (level.snakes.length) {
		case 1:
			drawScoreSP();
			break;
		default:
			drawScoreMP();
			break;
		}
	}

	private void drawScoreSP() {
		bufferGraphics.setColor(Color.BLACK);

		String string = STR_SCORESP + level.snakes[0].getScore();
		bufferGraphics.drawString(string, (Constants.CONTENT_WIDTH - getFontWidth(string, 45)) / 2, Constants.CONTENT_HEIGHT / 2);
	}

	private void drawScoreMP() {
		bufferGraphics.setColor(Color.BLACK);

		for (int i = 0; i < level.snakes.length; i++) {
			String string = STR_SCOREMP.replace("<index>", i + "") + level.snakes[i].getScore();
			bufferGraphics.drawString(string, (Constants.CONTENT_WIDTH - getFontWidth(string, 45)) / 2, Constants.CONTENT_HEIGHT / 2 + i * 80);
		}
	}

	private void drawCTC() {
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.drawString(STR_CTC, (Constants.CONTENT_WIDTH - getFontWidth(STR_CTC, 20)) / 2, Constants.CONTENT_HEIGHT - 50);
	}

	private int getFontWidth(String label, int newSize) {
		Font font = new Font("SanSarif", Font.BOLD, newSize);
		bufferGraphics.setFont(font);

		return bufferGraphics.getFontMetrics(font).stringWidth(label);
	}

	private int getFontHeight(String label, int newSize) {
		Font font = new Font("SanSarif", Font.BOLD, newSize);
		bufferGraphics.setFont(font);

		return (int) bufferGraphics.getFont().getLineMetrics(label, ((Graphics2D) bufferGraphics).getFontRenderContext()).getHeight();
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