package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.ScoreListEntry;
import control.Constants;

/**
 * @author Quirin Heiler, Stefan Kameter
 * @version 10.07.2015
 */
public class ScoreListIO {
	private static final String STR_ERROR = "CANNOT LOAD SCORELIST!";

	private static final String FILE_SCORELIST = Constants.DATAPATH + "scorelist.csv";
	private static final String TAG_SPLIT = ";";

	/**
	 * Loads the scoreList from 'FILE_SCORELIST'.
	 * 
	 * @return the scoreList
	 */
	public static List<ScoreListEntry> load() {
		List<ScoreListEntry> entries = new ArrayList<ScoreListEntry>();

		File file = new File(FILE_SCORELIST);

		if (!file.exists()) {
			System.err.println(STR_ERROR);
			return entries;
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (entries.size() == Constants.SCORELIST_ENTRIES_MAX)
					break;

				String[] values = line.split(TAG_SPLIT);
				entries.add(new ScoreListEntry(values[0], values[1], Integer.parseInt(values[2])));
			}
			reader.close();
		} catch (IOException e) {
			System.err.println(STR_ERROR);
			entries.clear();
		}

		return entries;
	}

	/**
	 * Saves the scoreList to 'FILE_SCORELIST'.
	 * 
	 * @param scoreList
	 *            the scoreList
	 */
	public static void save(List<ScoreListEntry> scoreList) {
		if (scoreList.size() == 0)
			return;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_SCORELIST)));

			for (ScoreListEntry entry : scoreList)
				writer.write(entry.getLevel() + TAG_SPLIT + entry.getName() + TAG_SPLIT + entry.getScore() + "\n");

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}