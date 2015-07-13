package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import model.Direction;
import model.Level;
import model.Snake;
import model.StaticCellObject;
import model.TilePosition;
import control.Constants;

/**
 * @author Quirin Heiler, Stefan Kameter
 * @version 10.07.2015
 */
public class LevelIO {
	private static final String STR_ERROR = "CANNOT LOAD LEVELS!";

	private static final String DIR_LEVELS = Constants.DATAPATH + "lvl/";

	private static final HashMap<String, Level> levels = new HashMap<String, Level>();

	/**
	 * Loads all Levels of the directory 'DIR_LEVELS'.
	 */
	public static void load() {
		levels.clear();

		File[] files = new File(DIR_LEVELS).listFiles();

		for (File file : files) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				int counter = 0;
				String line = null;

				String name = null;
				int width = -1;
				int height = -1;
				boolean endless = false;
				int defaultSpeed = -1;
				int itemNumber = -1;

				Snake[] snakes = null;
				StaticCellObject[] objects = null;

				while ((line = reader.readLine()) != null) {
					String[] values = line.split(";");
					switch (counter) {
					case 0:
						name = values[0];
						width = Integer.parseInt(values[1]);
						height = Integer.parseInt(values[2]);
						endless = Boolean.parseBoolean(values[3]);
						defaultSpeed = Integer.parseInt(values[4]);
						itemNumber = Integer.parseInt(values[5]);
						break;
					case 1:
						if (values.length == 1)
							snakes = new Snake[1];
						else
							snakes = new Snake[2];
						for (int i = 0; i < snakes.length; i++) {
							String[] vs = values[i].split(",");
							snakes[i] = new Snake(Integer.parseInt(vs[0]), new TilePosition(Integer.parseInt(vs[1]), Integer.parseInt(vs[2])), Direction.parseDirection(vs[3]));
							if (Boolean.parseBoolean(vs[4]))
								snakes[i].setPathfinder();
						}
						break;
					case 2:
						objects = new StaticCellObject[values.length / 2];
						for (int i = 0; i < objects.length; i++)
							objects[i] = new StaticCellObject(new TilePosition(Integer.parseInt(values[i * 2]), Integer.parseInt(values[i * 2 + 1])));
						break;
					}
					counter++;
				}
				reader.close();
				if (objects == null)
					objects = new StaticCellObject[0];
				levels.put(name, new Level(name, width, height, endless, defaultSpeed, itemNumber, snakes, objects));
			} catch (IOException e) {
				System.err.println(STR_ERROR);
				System.exit(1);
			}
		}
	}

	/**
	 * Returns the total number of Levels.
	 * 
	 * @return the total number of Levels
	 */
	public static int getLevelNumber() {
		return levels.size();
	}

	/**
	 * Returns the names of all the levels.
	 * 
	 * @return the names of all the levels
	 */
	public static Vector<String> getLevelNames() {
		Vector<String> vec = new Vector<String>();

		for (String name : levels.keySet())
			if (levels.get(name).isAllowed())
				vec.add(name);

		return vec;
	}

	/**
	 * Returns a chosen Level.
	 * 
	 * @param name
	 *            the name of the Level
	 * @return a chosen Level
	 */
	public static Level getLevel(String name) {
		return levels.get(name).clone();
	}
}