package view;

import input.KeyBoard;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

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

	private ScoreListPanel scoreListPanel;
	private GamePanel gameCanvas;

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
		add(gameCanvas = new GamePanel(), Comp.GAMECANVAS.getString());

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
		return gameCanvas;
	}

	/**
	 * Returns the ScoreListPanel.
	 * 
	 * @return ScoreListPanel
	 */
	public ScoreListPanel getScoreListPanel() {
		return scoreListPanel;
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
	 * Returns the buffer.
	 * 
	 * @return buffer
	 */
	public BufferedImage getBuffer() {
		return gameCanvas.getBuffer();
	}

	/**
	 * Changes, which Component is added.
	 * 
	 * @param comp
	 *            the Component, which has to be added
	 */
	public void changeComponent(Comp comp) {
		cardLayout.show(getContentPane(), comp.getString());
	}

	/**
	 * Sets a Level, and starts the GameThread.
	 * 
	 * @param snakes
	 *            the snakes
	 * @param cellObjects
	 *            the static CellObjects
	 * @param items
	 *            the list of default Items
	 */
	public void setLevel(Snake[] snakes, List<CellObject> cellObjects, List<Item> items) {
		gameCanvas.setLevel(snakes, cellObjects, items);

		start();
	}

	private void start() {
		gameThread.start();
	}

	/**
	 * Stops the Game.
	 */
	public void stop() {
		gameThread.stop();
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