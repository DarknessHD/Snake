package control;

import java.util.Collections;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.ScoreListEntry;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class GameTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private List<ScoreListEntry> entries;

	/**
	 * Sets the data.
	 * 
	 * @param entries
	 *            the entries of the scoreList
	 */
	public void setData(List<ScoreListEntry> entries) {
		this.entries = entries;

		Collections.sort(this.entries);

		fireTableStructureChanged();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "LEVEL";
		case 1:
			return "NAME";
		case 2:
			return "SCORE";
		default:
			return null;
		}
	}

	@Override
	public int getRowCount() {
		if (entries == null)
			return 0;
		return entries.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (entries == null)
			return null;
		switch (col) {
		case 0:
			return entries.get(row).getLevel();
		case 1:
			return entries.get(row).getName();
		case 2:
			return entries.get(row).getScore();
		default:
			return null;
		}
	}
}