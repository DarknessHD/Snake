package view;

import input.KeyBoard;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import model.Snake;
import model.cellobject.Apple;
import model.cellobject.CellObject;
import control.GameThread;

/**
 * @author Stefan Kameter
 * @version 28.06.2015
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * The path of data.
	 */
	public static final String DATAPATH = "data";
	private static final String TITLE = "Snake";

	private static GameFrame instance;

	/**
	 * The Singleton.
	 * 
	 * @return GameFrame the instance
	 */
	public static GameFrame getInstance() {
		if (instance == null)
			instance = new GameFrame();
		return instance;
	}

	private GameCanvas gameCanvas;
	private GameMenuPanel gmp;

	private GameThread gameThread;
	private int score;

	private GameFrame() {
		setScore(0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		initComponents();
		initListener();

		pack();
		setLocationRelativeTo(null);
		setResizable(false);

		gameThread = new GameThread();
	}

	private void initComponents() {
		List<CellObject> cellObjects = new ArrayList<CellObject>();
		cellObjects.add(new Apple(new Point(10, 10)));
		gameCanvas = new GameCanvas(null, cellObjects);
		gmp = new GameMenuPanel();

		changeComponent(Comp.GAMEMENUPANEL);
	}

	private void initListener() {
		addKeyListener(KeyBoard.getInstance());
	}

	/**
	 * @return player snake
	 */
	public Snake getSnake() {
		return gameCanvas.getSnake();
	}

	/**
	 * Sets the a score value.
	 * 
	 * @param score
	 *            the new score
	 */
	public void setScore(int score) {
		this.score = score;
		setTitle(TITLE);// + " - Score: " + this.score);
	}

	private Comp lastComponent;

	/**
	 * Changes, which Component is added.
	 * 
	 * @param comp
	 *            the Component, which has to be added
	 */
	public void changeComponent(Comp comp) {
		if (lastComponent != null)
			switch (lastComponent) {
			case GAMECANVAS:
				gameCanvas.revalidate();
				remove(gameCanvas);
				break;
			case GAMEMENUPANEL:
				gmp.revalidate();
				remove(gmp);
				break;
			}

		lastComponent = comp;

		switch (comp) {
		case GAMECANVAS:
			add(gameCanvas, BorderLayout.CENTER);
			break;
		case GAMEMENUPANEL:
			add(gmp, BorderLayout.CENTER);
			break;
		}
	}

	/**
	 * Starts the thread of GameLoop.
	 */
	public void start() {
		gameThread.start();
	}

	/**
	 * Repaint only the GameCanvas.
	 */
	public void repaintGameCanvas() {
		gameCanvas.repaint();
	}

	/**
	 * Executes onSnakeHitCellObject, if necessary.
	 */
	public void onMove() {
		gameCanvas.onMove();
	}

	/**
	 * Starts the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameFrame frame = getInstance();
		frame.setVisible(true);
	}
}