package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import view.popup.ChooseLevelPanel;
import control.Comp;

/**
 * @author Stefan Kameter
 * @version 04.07.2015
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
				JDialog dialog = new JDialog(GameFrame.getInstance(), "Choose Level", true);
				dialog.setResizable(false);
				ChooseLevelPanel cLP = new ChooseLevelPanel(dialog);
				dialog.add(cLP);
				dialog.pack();
				dialog.setLocationRelativeTo(GameFrame.getInstance());
				dialog.setVisible(true);
			}
		});

		scoreList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameFrame.getInstance().getScoreListPanel().setScoreList(GameFrame.getInstance().getScoreList());

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