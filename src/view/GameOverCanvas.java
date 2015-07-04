package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import model.Direction;
import model.Snake;
import control.Comp;

/**
 * @author Stefan Kameter
 * @version 04.07.2015
 */
public class GameOverCanvas extends Canvas {
	private static final long serialVersionUID = 1L;

	private BufferedImage buffer;
	private Graphics bufferGraphics;

	/**
	 * Creates an instance of GameOverCanvas.
	 */
	public GameOverCanvas() {
		setPreferredSize(new Dimension(GameCanvas.CANVAS_WIDTH, GameCanvas.CANVAS_HEIGHT));

		setFocusable(true);

		initListener();
	}

	private void initListener() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO add score to ScoreList, if only one snake

				GameFrame.getInstance().changeComponent(Comp.GAMEMENUPANEL);
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		if (bufferGraphics == null) {
			buffer = new BufferedImage(GameCanvas.CANVAS_WIDTH, GameCanvas.CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			bufferGraphics = buffer.getGraphics();
		}

		bufferGraphics.setColor(getBackground());
		bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

		Snake[] snakes = GameFrame.getInstance().getGameCanvas().getSnakes();
		snakes = new Snake[1];
		snakes[0] = new Snake(4, new Point(4, 4), Direction.LEFT);

		switch (snakes.length) {
		case 1:
			drawOneSnakeSituation(bufferGraphics, snakes[0]);
			break;
		case 2:
			drawTwoSnakesSituation(bufferGraphics, snakes);
			break;
		}

		bufferGraphics.setFont(new Font("SanSarif", Font.BOLD, 20));
		bufferGraphics.setColor(Color.BLACK);
		String string = "Click to continue";
		int width = bufferGraphics.getFontMetrics(bufferGraphics.getFont()).stringWidth(string);
		bufferGraphics.drawString(string, (GameCanvas.CANVAS_WIDTH - width) / 2, GameCanvas.CANVAS_HEIGHT - 30);

		g.drawImage(buffer, 5, 5, buffer.getWidth(), buffer.getHeight(), null);
	}

	private void drawOneSnakeSituation(Graphics g, Snake snake) {
		// TODO
	}

	private void drawTwoSnakesSituation(Graphics g, Snake[] snakes) {
		// TODO
	}
}