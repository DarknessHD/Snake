package view;

import input.KeyBoard;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

	private GameMenuPanel gameMenuPanel;
	private ScoreListPanel scoreListPanel;
	private GameCanvas gameCanvas;
	private GameOverCanvas gameOverCanvas;

	private GameThread gameThread;

	private List<ScoreListEntry> scoreList;

	private GameFrame() {
		setTitle(TITLE);

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
		gameCanvas = new GameCanvas();
		gameOverCanvas = new GameOverCanvas();

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
	 * Returns the GameCanvas.
	 * 
	 * @return GameCanvas
	 */
	public GameCanvas getGameCanvas() {
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

	private Comp lastComponent;

	/**
	 * Changes, which Component is added.
	 * 
	 * @param comp
	 *            the Component, which has to be added
	 */
	public void changeComponent(Comp comp) {
		// TODO flackern, wenn vorhanden

		if (lastComponent != null)
			switch (lastComponent) {
			case GAMEMENUPANEL:
				gameMenuPanel.revalidate();
				remove(gameMenuPanel);
				break;
			case SCORELISTPANEL:
				scoreListPanel.revalidate();
				remove(scoreListPanel);
				break;
			case GAMECANVAS:
				gameCanvas.revalidate();
				remove(gameCanvas);
				break;
			case GAMEOVERCANVAS:
				gameOverCanvas.revalidate();
				remove(gameOverCanvas);
				break;
			}

		lastComponent = comp;

		switch (comp) {
		case GAMEMENUPANEL:
			add(gameMenuPanel, BorderLayout.CENTER);
			break;
		case SCORELISTPANEL:
			add(scoreListPanel, BorderLayout.CENTER);
			break;
		case GAMECANVAS:
			add(gameCanvas, BorderLayout.CENTER);
			break;
		case GAMEOVERCANVAS:
			add(gameOverCanvas, BorderLayout.CENTER);
			break;
		}

		revalidate();
		repaint();
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
	 * Increases the game-speed.
	 * 
	 * @param speedAddition
	 *            the value that the speed gets in-/decreased by
	 */
	public void changeSpeed(int speedAddition) {
		gameThread.changeSpeed(speedAddition);
	}

	/**
	 * Method has to be called, if game is lost.
	 */
	public void lost() {
		gameThread.stop();
		GameFrame.getInstance().changeComponent(Comp.GAMEOVERCANVAS);
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