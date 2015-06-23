package view;

import input.KeyBoard;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import control.GameThread;

/**
 * @author Stefan
 * @version 22.06.2015
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

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

	// private GameCanvas gameCanvas;

	private GameThread gameThread;
	private int score;

	private GameFrame() {
		addScore(0);
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
		add(/* gameCanvas = */new GameCanvas(null), BorderLayout.CENTER);
	}

	private void initListener() {
		addKeyListener(KeyBoard.getInstance());
	}

	/**
	 * Adds a score value to the final score.
	 * 
	 * @param score
	 *            the addition to the score
	 */
	public void addScore(int score) {
		this.score += score;
		setTitle(TITLE + " - Score: " + this.score);
	}

	/**
	 * Starts the thread of GameLoop.
	 */
	public void start() {
		gameThread.start();
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