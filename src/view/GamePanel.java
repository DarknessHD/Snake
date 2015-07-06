package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import model.CellObject;
import model.Level;
import model.Snake;
import model.SnakeSegment;
import model.item.Item;
import model.staticobjects.StaticCellObject;
import control.Comp;
import control.Constants;
import control.ItemSpawner;
import control.ShiftType;

/**
 * @author Stefan Kameter
 * @version 02.07.2015
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String STR_PAUSED = "Game Paused";
	private static final String STR_GAMEOVER = "Game Over";
	private static final String STR_SCORESP = "Score: ";
	private static final String STR_SCOREMP = "(Snake <index>) Score: ";
	private static final String STR_CTC = "Click To Continue";

	private Level level;

	private BufferedImage buffer;
	private Graphics bufferGraphics;

	private boolean paused;
	private boolean gameOver;

	/**
	 * Creates a new GamePanel instance.
	 */
	public GamePanel() {
		paused = false;
		gameOver = false;

		setPreferredSize(new Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT));

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
				} else if (paused)
					setPaused(false);
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
		CellObject head = level.snakes[index].getHead();
		Point hp = head.getPosition();

		for (SnakeSegment seg : level.snakes[index].getSegments()) {
			if (seg.equals(head))
				continue;
			if (hp.equals(seg.getPosition()))
				seg.onSnakeHitCellObject(level.snakes[index]);
		}

		for (SnakeSegment seg : level.snakes[(index + 1) % 2].getSegments()) {
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

		for (int i = 0; i < level.items.size(); i++) {
			Item item = level.items.get(i);
			if (hp.equals(item.getPosition())) {
				item.onSnakeHitCellObject(level.snakes[index]);
				level.items.remove(i);
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
			for (Item i : level.items)
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

	@Override
	public void paint(Graphics g) {
		if (level != null) {
			if (bufferGraphics == null) {
				buffer = new BufferedImage(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
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

		for (int y = 0; y < Constants.LEVEL_HEIGHT; y++)
			for (int x = 0; x < Constants.LEVEL_WIDTH; x++)
				bufferGraphics.drawRect(x << Constants.TILE_SIZE_BW, y << Constants.TILE_SIZE_BW, Constants.TILE_SIZE, Constants.TILE_SIZE);

		// Items
		for (Item i : level.items) {
			Point p = i.getPosition();
			bufferGraphics.drawImage(i.getImage(), p.x << Constants.TILE_SIZE_BW, p.y << Constants.TILE_SIZE_BW, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
		}

		// Snakes
		for (Snake snake : level.snakes)
			for (SnakeSegment s : snake.getSegments()) {
				Point p = s.getPosition();
				bufferGraphics.drawImage(s.getImage(), p.x << Constants.TILE_SIZE_BW, p.y << Constants.TILE_SIZE_BW, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
			}

		// StaticCellObjects
		for (StaticCellObject obj : level.staticCellObjects) {
			Point p = obj.getPosition();
			bufferGraphics.drawImage(obj.getImage(), p.x << Constants.TILE_SIZE_BW, p.y << Constants.TILE_SIZE_BW, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
		}
	}

	private void drawPaused() {
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.drawString(STR_PAUSED, (Constants.CANVAS_WIDTH - getFontWidth(STR_PAUSED, 60)) / 2, Constants.CANVAS_HEIGHT / 4);

		drawScore();

		drawCTC();
	}

	private void drawGameOver() {
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.drawString(STR_GAMEOVER, (Constants.CANVAS_WIDTH - getFontWidth(STR_GAMEOVER, 60)) / 2, Constants.CANVAS_HEIGHT / 4);

		drawWinning();

		drawScore();

		drawCTC();
	}

	private void drawScore() {
		switch (level.snakes.length) {
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

		String string = STR_SCORESP + level.snakes[0].getScore();
		bufferGraphics.drawString(string, (Constants.CANVAS_WIDTH - getFontWidth(string, 45)) / 2, Constants.CANVAS_HEIGHT / 2);
	}

	private void drawScoreMP() {
		bufferGraphics.setColor(Color.BLACK);

		for (int i = 0; i < level.snakes.length; i++) {
			String string = STR_SCOREMP.replace("<index>", i + "") + level.snakes[i].getScore();
			bufferGraphics.drawString(string, (Constants.CANVAS_WIDTH - getFontWidth(string, 45)) / 2, Constants.CANVAS_HEIGHT / 2 + i * 80);
		}
	}

	private void drawWinning() {
		// TODO whether you have won or lost
		bufferGraphics.setColor(Color.ORANGE);
	}

	private void drawCTC() {
		bufferGraphics.setColor(Color.BLACK);

		bufferGraphics.drawString(STR_CTC, (Constants.CANVAS_WIDTH - getFontWidth(STR_CTC, 20)) / 2, Constants.CANVAS_HEIGHT - 50);
	}

	private int getFontWidth(String label, int newSize) {
		Font font = new Font("SanSarif", Font.BOLD, newSize);
		bufferGraphics.setFont(font);

		// int height = (int) font.getLineMetrics(label, ((Graphics2D) bufferGraphics).getFontRenderContext()).getHeight()
		return bufferGraphics.getFontMetrics(font).stringWidth(label);
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