package view;

import input.KeyBoard;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Level;
import model.ScoreListEntry;
import control.Comp;
import control.Constants;
import control.GameThread;

/**
 * @author Stefan Kameter
 * @version 02.07.2015
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final String STR_TITLE = "Snake " + Constants.VERSION.trim();
	private static final Object STR_YOURNAME = "Your name:";

	private static GameFrame instance;

	/**
	 * The Singleton.
	 * 
	 * @return the GameFrame instance
	 */
	public static GameFrame getInstance() {
		if (instance == null)
			instance = new GameFrame();
		return instance;
	}

	private GameMenuPanel gameMenuPanel;
	private ScoreListPanel scoreListPanel;
	private GamePanel gamePanel;

	private GameThread gameThread;

	private List<ScoreListEntry> scoreList;

	private GameFrame() {
		setTitle(STR_TITLE);

		setLayout(new BorderLayout());

		initComponents();
		initListener();

		pack();
		setLocationRelativeTo(null);
		setResizable(false);

		gameThread = new GameThread();

		scoreList = new ArrayList<ScoreListEntry>();// TODO load (scoreList, ...)
	}

	private void initComponents() {
		gameMenuPanel = new GameMenuPanel();
		scoreListPanel = new ScoreListPanel();
		gamePanel = new GamePanel();

		changeComponent(Comp.GAMEMENUPANEL);
	}

	private void initListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});

		addKeyListener(KeyBoard.getInstance());
	}

	/**
	 * Returns the GamePanel.
	 * 
	 * @return GamePanel
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	/**
	 * Updates the ScoreListPanel.
	 */
	public void updateScoreListPanel() {
		scoreListPanel.setScoreList(scoreList);
	}

	/**
	 * Returns the scoreList.
	 * 
	 * @return scoreList
	 */
	public List<ScoreListEntry> getScoreList() {
		return scoreList;
	}

	/**
	 * Changes, which Component is shown.
	 * 
	 * @param comp
	 *            the Component, which has to be shown
	 */
	public void changeComponent(Comp comp) {
		remove(gameMenuPanel);
		remove(scoreListPanel);
		remove(gamePanel);

		switch (comp) {
		case GAMEMENUPANEL:
			add(gameMenuPanel, BorderLayout.CENTER);
			break;
		case SCORELISTPANEL:
			add(scoreListPanel, BorderLayout.CENTER);
			break;
		case GAMEPANEL:
			add(gamePanel, BorderLayout.CENTER);
			break;
		}

		revalidate();
		repaint();
	}

	/**
	 * Sets the Level.
	 * 
	 * @param level
	 *            the level name
	 * @param snakes
	 *            the snakes
	 * @param staticCellObjects
	 *            the StaticCellObjects
	 * @param items
	 *            the list of default Items
	 * @param defaultSpeed
	 *            the default speed
	 * @return whether Level could be initialized
	 */
	public boolean setLevel(Level level) {
		gamePanel.setLevel(level);
		if (level.init())
			return true;
		gamePanel.setLevel(null);
		return false;
	}

	/**
	 * Adds a ScoreListEntry to the scoreList if necessary.
	 * 
	 * @param level
	 *            the level name
	 * @param score
	 *            new score
	 */
	public void addToScoreList(String level, int score) {
		if (score == 0)
			return;
		if (scoreList.size() < Constants.SCORELISTENTRIES || isBetter(score)) {
			String name = JOptionPane.showInputDialog(this, STR_YOURNAME);
			if (name != null && !name.trim().isEmpty())
				scoreList.add(new ScoreListEntry(level, name.trim(), score));
		}
	}

	private boolean isBetter(int score) {
		for (ScoreListEntry e : scoreList)
			if (e.getScore() < score)
				return true;
		return false;
	}

	/**
	 * Increases the game-speed.
	 * 
	 * @param speedAddition
	 *            the value that the speed gets in-/decreased by
	 */
	public void changeSpeed(int speedAddition) {
		gameThread.changeSpeed(speedAddition);
	}

	/**
	 * Starts the Game.
	 * 
	 * @param defaultSpeed
	 *            the defaultSpeed
	 */
	public void start(int defaultSpeed) {
		gameThread.start(defaultSpeed);
	}

	/**
	 * Stops the Game.
	 */
	public void stop() {
		gameThread.stop();
	}

	/**
	 * Has to be called, if game will be exited.
	 */
	public void exit() {
		// TODO save (scoreList, ...)
		System.exit(0);
	}

	/**
	 * Starts the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		getInstance().setVisible(true);
	}
}