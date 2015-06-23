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

	private List<CellObject> cellObjects;

	/**
	 * Creates a new GameCanvas instance.
	 * 
	 * @param cellObjects
	 *            the list of default CellObjects
	 */
	public GameCanvas(List<CellObject> cellObjects) {
		this.cellObjects = cellObjects;
		this.cellObjects = new LinkedList<CellObject>(); // TODO

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

		for (CellObject d : cellObjects) {
			Point position = d.getPosition();
			bufferGraphics.drawImage(d.getImage(), 5 + position.x * TILE_SIZE, 5 + position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
		}

		g.drawImage(buffer, 0, 0, WIDTH, HEIGHT, null);
	}
}