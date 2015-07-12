package launcher;

import io.LevelIO;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.GameFrame;
import control.Constants;

/**
 * @author Stefan Kameter
 * @version 11.07.2015
 */
public class Launcher extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String STR_TITLE = "Snake - Launcher";
	private static final String STR_START = "Start Game";
	private static final String STR_CHECK = "Search Levels";
	private static final String STR_LEVELNUMBER = "Total number of Levels:   <number>";
	private static final String[] STR_LABELS = { "Tile Size:", "Speed Minimum:", "Level Height:", "Despawntime:", "Level Width:", "Speed Maximum:" };
	private static int[] CONTENTS;

	private static JFrame frame;

	private JLabel[] labels;
	private JTextField[] contents;
	private JLabel levelNumber;
	private JComboBox<String> levels;
	private JButton check;
	private JButton start;

	private Launcher() {
		setLayout(null);

		setPreferredSize(new Dimension(400, 215));

		initComponents();
		initListener();
	}

	private void initComponents() {
		labels = new JLabel[STR_LABELS.length];
		for (int i = 0; i < labels.length; i++) {
			add(labels[i] = new JLabel(STR_LABELS[i]));
			labels[i].setBounds(10 + (i % 2) * 200, 10 + (i % 3) * 35, 100, 25);
		}

		contents = new JTextField[STR_LABELS.length];
		for (int i = 0; i < labels.length; i++) {
			add(contents[i] = new JTextField(CONTENTS[i] + ""));
			contents[i].setBounds(120 + (i % 2) * 200, 10 + (i % 3) * 35, 50, 25);
		}

		add(levelNumber = new JLabel(STR_LEVELNUMBER.replace("<number>", LevelIO.getLevelNumber() + "")));
		levelNumber.setBounds(10, 112, 200, 20);

		add(levels = new JComboBox<String>(new GameComboBoxModel()));
		((GameComboBoxModel) levels.getModel()).setData(LevelIO.getLevelNames());
		levels.setBounds(10, 137, 250, 30);

		add(check = new JButton(STR_CHECK));
		check.setBounds(270, 137, 120, 30);

		add(start = new JButton(STR_START));
		start.setBounds(10, 180, 380, 30);
	}

	private void initListener() {
		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!save())
					return;

				((GameComboBoxModel) levels.getModel()).setData(LevelIO.getLevelNames());
			}
		});

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!save())
					return;

				start();
			}
		});
	}

	/**
	 * Returns a JTextField.
	 * 
	 * @param index
	 *            the index of the JTextField
	 * @return a JTextField
	 */
	public JTextField getContent(int index) {
		return contents[index];
	}

	private boolean save() {
		try {
			Constants.TILE_SIZE = Integer.parseInt(contents[0].getText());
			Constants.SPEED_MIN = Integer.parseInt(contents[1].getText());
			Constants.LEVEL_HEIGHT = Integer.parseInt(contents[2].getText());
			Constants.DESPAWNTIME = Integer.parseInt(contents[3].getText());
			Constants.LEVEL_WIDTH = Integer.parseInt(contents[4].getText());
			Constants.SPEED_MAX = Integer.parseInt(contents[5].getText());
		} catch (Exception exc) {
			return false;
		}
		Constants.save();
		return true;
	}

	private static void start() {
		if (frame != null)
			frame.setVisible(false);
		Constants.calc();
		GameFrame.start();
	}

	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            the start arguments
	 */
	public static void main(String[] args) {
		Constants.load();
		LevelIO.load();
		if (!Constants.LAUNCHER) {
			start();
			return;
		}

		CONTENTS = new int[STR_LABELS.length];
		CONTENTS[0] = Constants.TILE_SIZE;
		CONTENTS[1] = Constants.SPEED_MIN;
		CONTENTS[2] = Constants.LEVEL_HEIGHT;
		CONTENTS[3] = Constants.DESPAWNTIME;
		CONTENTS[4] = Constants.LEVEL_WIDTH;
		CONTENTS[5] = Constants.SPEED_MAX;

		frame = new JFrame(STR_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Launcher());
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
	}

	private class GameComboBoxModel extends DefaultComboBoxModel<String> {
		private static final long serialVersionUID = 1L;

		private Vector<String> names;
		private String selectedItem;

		public void setData(Vector<String> names) {
			this.names = names;
			selectedItem = null;

			if (names.size() > 0) {
				setSelectedItem(names.get(0));
				repaint();
			}
		}

		@Override
		public String getElementAt(int index) {
			if (names == null)
				return null;
			return names.get(index);
		}

		@Override
		public int getSize() {
			if (names == null)
				return 0;
			return names.size();
		}

		@Override
		public Object getSelectedItem() {
			return selectedItem;
		}

		@Override
		public void setSelectedItem(Object obj) {
			if (names == null)
				return;
			for (String name : names)
				if (name.equals(obj))
					selectedItem = name;
		}
	}
}