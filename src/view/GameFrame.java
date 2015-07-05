package view;

import input.KeyBoard;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.CellObject;
import model.Item;
import model.ScoreListEntry;
import model.Snake;
import control.Comp;
import control.GameThread;

/**
 * @author Stefan Kameter
 * @version 02.07.2015
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * The path of data.
	 */
	public static final String DATAPATH = "data";
	private static final String TITLE = "Snake";
	private static final int SCORELISTENTRIES = 10;

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

	private ScoreListPanel scoreListPanel;
	private GamePanel gamePanel;

	private GameThread gameThread;

	private CardLayout cardLayout;

	private List<ScoreListEntry> scoreList;

	private GameFrame() {
		setTitle(TITLE);

		setLayout(cardLayout = new CardLayout());

		initComponents();
		initListener();

		pack();
		setLocationRelativeTo(null);
		setResizable(false);

		gameThread = new GameThread();

		scoreList = new ArrayList<ScoreListEntry>();// TODO load (scoreList, ...)
	}

	private void initComponents() {
		add(new GameMenuPanel(), Comp.GAMEMENUPANEL.getString());
		add(scoreListPanel = new ScoreListPanel(), Comp.SCORELISTPANEL.getString());
		add(gamePanel = new GamePanel(), Comp.GAMEPANEL.getString());

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
		cardLayout.show(getContentPane(), comp.getString());
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
	 * @param defaultSpeed
	 *            the default speed
	 */
	public void setLevel(String level, Snake[] snakes, List<CellObject> staticObjects, List<Item> items, int defaultSpeed) {
		gamePanel.setLevel(level, snakes, staticObjects, items);
		gameThread.start(defaultSpeed);
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
		if (scoreList.size() < SCORELISTENTRIES || isBetter(score)) {
			String name = JOptionPane.showInputDialog(this, "Your name:");
			if (name != null && !name.isEmpty())
				scoreList.add(new ScoreListEntry(level, name, score));
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