package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.ScoreListEntry;
import control.Comp;
import control.Constants;
import control.GameTableModel;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class ScoreListPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private JButton back;

	/**
	 * Creates an instance of ScoreListPanel.
	 */
	public ScoreListPanel() {
		setLayout(null);

		setPreferredSize(new Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT));

		initComponents();
		initListener();
	}

	private void initComponents() {
		JScrollPane scroll = null;
		add(scroll = new JScrollPane(table = new JTable(new GameTableModel())));
		scroll.setBounds((Constants.CANVAS_WIDTH - 500) / 2, (Constants.CANVAS_HEIGHT - 50 - 183) / 2, 500, 183);

		add(back = new JButton("Back"));
		back.setBounds((Constants.CANVAS_WIDTH - 200) / 2, Constants.CANVAS_HEIGHT - 40, 200, 30);
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
	 * Sets a scoreList.
	 * 
	 * @param entries
	 *            the entries of the scoreList
	 */
	public void setScoreList(List<ScoreListEntry> entries) {
		((GameTableModel) table.getModel()).setData(entries);
	}
}