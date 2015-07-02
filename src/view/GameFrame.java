package view;

import input.KeyBoard;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.JFrame;

import model.Item;
import model.Snake;
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

	private GameCanvas gameCanvas;
	private GameMenuPanel gmp;
	private ScoreListPanel slp;

	private GameThread gameThread;

	private GameFrame() {
		setTitle(TITLE);
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
		gameCanvas = new GameCanvas();
		gmp = new GameMenuPanel();
		slp = new ScoreListPanel();

		changeComponent(Comp.GAMEMENUPANEL);
	}

	private void initListener() {
		addKeyListener(KeyBoard.getInstance());
	}

	/**
	 * Returns the snakes.
	 * 
	 * @return snakes
	 */
	public Snake[] getSnakes() {
		return gameCanvas.getSnakes();
	}

	/**
	 * Sets a Level, and starts the GameThread.
	 * 
	 * @param snakes
	 *            the snakes
	 * @param items
	 *            the list of default Items
	 */
	public void setLevel(Snake[] snakes, List<Item> items) {
		gameCanvas.setLevel(snakes, items);

		start();
	}

	/**
	 * Adds a Item.
	 * 
	 * @param item
	 *            the Item
	 */
	public void addItem(Item item) {
		gameCanvas.addItem(item);
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
			case SCORELISTPANEL:
				slp.revalidate();
				remove(slp);
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
		case SCORELISTPANEL:
			add(slp, BorderLayout.CENTER);
			break;
		}

		repaint();
	}

	private void start() {
		gameThread.start();
	}

	/**
	 * Repaint only the GameCanvas.
	 */
	public void repaintGameCanvas() {
		gameCanvas.repaint();
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
	 * Executes onSnakeHitCellObject, if necessary.
	 * 
	 * @param index
	 *            the desired snake
	 */
	public void onMove(int index) {
		gameCanvas.onMove(index);
	}

	/**
	 * Checks whether an item is already placed at that position.
	 * 
	 * @param position
	 *            the position to check
	 * @return whether an item is already placed at that position
	 */
	public boolean checkPosition(Point position) {
		return gameCanvas.checkPosition(position);
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