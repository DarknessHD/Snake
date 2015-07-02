package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Item;
import model.ItemSpawner;
import model.cellobject.Apple;
import model.cellobject.RottenApple;

/**
 * @author Stefan Kameter
 * @version 29.06.2015
 */
public class GameMenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton start;
	private JButton scoreList;
	private JButton exit;

	/**
	 * Creates an instance of GameMenuPanel.
	 */
	public GameMenuPanel() {
		setLayout(null);

		setPreferredSize(new Dimension(GameCanvas.CANVAS_WIDTH, GameCanvas.CANVAS_HEIGHT));

		initComponents();
		initListener();
	}

	private void initComponents() {
		add(start = new JButton("Start Game"));
		start.setBounds((GameCanvas.CANVAS_WIDTH - 200) / 2, GameCanvas.CANVAS_HEIGHT / 4 - 15, 200, 30);

		add(scoreList = new JButton("Score List"));
		scoreList.setBounds((GameCanvas.CANVAS_WIDTH - 200) / 2, (GameCanvas.CANVAS_HEIGHT / 4) * 2 - 15, 200, 30);

		add(exit = new JButton("Exit Game"));
		exit.setBounds((GameCanvas.CANVAS_WIDTH - 200) / 2, (GameCanvas.CANVAS_HEIGHT / 4) * 3 - 15, 200, 30);
	}

	private void initListener() {
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameFrame.getInstance().changeComponent(Comp.GAMECANVAS);

				// TODO choose, load, set level
				List<Item> items = new ArrayList<Item>();
				for (int i = 0; i < 3; i++)
					items.add(ItemSpawner.getRandomItem());
				GameFrame.getInstance().setLevel(null, items);
			}
		});

		scoreList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameFrame.getInstance().changeComponent(Comp.SCORELISTPANEL);

				// TODO load, set scoreList
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}