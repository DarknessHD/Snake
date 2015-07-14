package view;

import input.KeyBoard;
import io.ImageHolder;
import io.ScoreListIO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
 * @version 08.07.2015
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final String STR_TITLE = ("Snake " + Constants.VERSION).trim();
	private static final Object STR_YOURNAME = "Your name:";

	/**
	 * The image in the left upper corner of the frame.
	 */
	public static final String CORNERIMAGE = "apple";

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
		setIconImage(ImageHolder.getImage(CORNERIMAGE));

		setLayout(new BorderLayout());

		initComponents();
		initListener();

		pack();
		setLocationRelativeTo(null);
		setResizable(false);

		gameThread = new GameThread();

		scoreList = ScoreListIO.load();
		scoreListPanel.setScoreList(scoreList);

		setBackground(Color.LIGHT_GRAY);
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
		if (scoreList.size() >= Constants.SCORELIST_ENTRIES_MAX) {
			ScoreListEntry entry = null;
			if ((entry = isBetter(score)) != null)
				scoreList.remove(entry);
			else
				return;
		}

		String name = JOptionPane.showInputDialog(this, STR_YOURNAME);
		if (name != null && !name.trim().isEmpty())
			scoreList.add(new ScoreListEntry(level, name.trim(), score));
		scoreListPanel.setScoreList(scoreList);
	}

	private ScoreListEntry isBetter(int score) {
		ScoreListEntry entry = null;
		int lastScore = score;
		for (ScoreListEntry e : scoreList) {
			int sc = e.getScore();
			if (sc < score && sc < lastScore) {
				entry = e;
				lastScore = sc;
			}
		}
		return entry;
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
		ScoreListIO.save(scoreList);
		System.exit(0);
	}

	/**
	 * Starts the game.
	 */
	public static void start() {
		getInstance().setVisible(true);
	}
}