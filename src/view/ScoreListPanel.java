package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Stefan Kameter
 * @version 02.07.2015
 */
public class ScoreListPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton back;

	/**
	 * Creates an instance of ScoreListPanel.
	 */
	public ScoreListPanel() {
		setLayout(null);

		setPreferredSize(new Dimension(GameCanvas.CANVAS_WIDTH, GameCanvas.CANVAS_HEIGHT));

		initComponents();
		initListener();
	}

	private void initComponents() {
		add(back = new JButton("Back"));
		back.setBounds((GameCanvas.CANVAS_WIDTH - 200) / 2, GameCanvas.CANVAS_HEIGHT - 40, 200, 30);
	}

	private void initListener() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameFrame.getInstance().changeComponent(Comp.GAMEMENUPANEL);
			}
		});
	}

	/**
	 * Sets a ScoreList.
	 */
	public void setScoreList() {
		// TODO
	}
}
