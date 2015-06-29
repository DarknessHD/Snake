package view;

import input.KeyBoard;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.Snake;
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
		add(gameCanvas = new GameCanvas(null, null), BorderLayout.CENTER);
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
	 * Starts the thread of GameLoop.
	 */
	public void start() {
		gameThread.start();
	}

	/**
	 * Sets the a score value.
	 * 
	 * @param score
	 *            the new score
	 */
	public void setScore(int score) {
		this.score = score;
		setTitle(TITLE + " - Score: " + this.score);
	}

	/**
	 * Repaint only the GameCanvas.
	 */
	public void repaintGameCanvas() {
		gameCanvas.repaint();
	}

	/**
	 * Starts the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameFrame frame = getInstance();
		frame.setVisible(true);
		frame.start();
	}
}