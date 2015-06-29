package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Hoodle
 * @version 29.06.2015
 */
public class GameMenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton start;
	// TODO private JButton options;
	// TODO private JButton scoreList;
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

		add(exit = new JButton("Exit Game"));
		exit.setBounds((GameCanvas.CANVAS_WIDTH - 200) / 2, (GameCanvas.CANVAS_HEIGHT / 4) * 3 - 15, 200, 30);
	}

	private void initListener() {
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameFrame.getInstance().changeComponent(Comp.GAMECANVAS);
				// TODO choose level
				GameFrame.getInstance().start();
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