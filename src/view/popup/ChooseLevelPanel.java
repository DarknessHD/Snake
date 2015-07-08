package view.popup;

import io.IOSimulator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import view.GameFrame;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class ChooseLevelPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JDialog dialog;

	private JComboBox<String> levels;
	private JButton accept;

	/**
	 * Creates an instance of ChooseLevelPanel.
	 * 
	 * @param dialog
	 *            the dialog
	 */
	public ChooseLevelPanel(JDialog dialog) {
		this.dialog = dialog;

		setPreferredSize(new Dimension(250, 100));

		setLayout(null);

		initComponents();
		initListener();
	}

	private void initComponents() {
		add(levels = new JComboBox<String>(/* TODO */IOSimulator.getLevelNames()));
		levels.setBounds(25, 10, 200, 30);

		add(accept = new JButton("Accept"));
		accept.setBounds(65, 65, 120, 25);
	}

	private void initListener() {
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);

				// TODO load, set chosen level
				GameFrame.getInstance().setLevel(IOSimulator.getLevel(levels.getSelectedItem().toString()));
			}
		});
	}
}