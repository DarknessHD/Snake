package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import model.Level;
import view.popup.ChooseLevelPanel;
import control.Comp;
import control.Constants;

/**
 * @author Stefan Kameter
 * @version 04.07.2015
 */
public class GameMenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String STR_START = "Start Game";
	private static final String STR_SCORELIST = "Score List";
	private static final String STR_EXIT = "Exit Game";
	private static final String STR_CHOOSELEVEL = "Choose Level";

	private JButton start;
	private JButton scoreList;
	private JButton exit;

	/**
	 * Creates an instance of GameMenuPanel.
	 */
	public GameMenuPanel() {
		setLayout(null);

		setPreferredSize(new Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT));

		initComponents();
		initListener();
	}

	private void initComponents() {
		add(start = new JButton(STR_START));
		start.setBounds((Constants.CANVAS_WIDTH - 200) / 2, Constants.CANVAS_HEIGHT / 4 - 15, 200, 30);

		add(scoreList = new JButton(STR_SCORELIST));
		scoreList.setBounds((Constants.CANVAS_WIDTH - 200) / 2, (Constants.CANVAS_HEIGHT / 4) * 2 - 15, 200, 30);

		add(exit = new JButton(STR_EXIT));
		exit.setBounds((Constants.CANVAS_WIDTH - 200) / 2, (Constants.CANVAS_HEIGHT / 4) * 3 - 15, 200, 30);
	}

	private void initListener() {
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// JDialog dialog = new JDialog(GameFrame.getInstance(), STR_CHOOSELEVEL, true);
				// dialog.setResizable(false);
				// dialog.add(new ChooseLevelPanel(dialog));
				// dialog.pack();
				// dialog.setLocationRelativeTo(GameFrame.getInstance());
				// dialog.setVisible(true);
				GameFrame.getInstance().setLevel(new Level("hardcoded testlevel", 30, 20, true, 5, 5, null, null));
			}
		});

		scoreList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameFrame.getInstance().updateScoreListPanel();
				GameFrame.getInstance().changeComponent(Comp.SCORELISTPANEL);
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameFrame.getInstance().exit();
			}
		});
	}
}