package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Stefan Kameter
 * @version 12.07.2015
 */
public class Constants {
	private static final String STR_ERROR = "Corupted INI!";

	private static final String INI = "snake.ini";
	private static final String STR_COMMENT = "Snake";

	private static Properties props;

	/**
	 * The path of data.
	 */
	public static String DATAPATH;
	/**
	 * Game version.
	 */
	public static String VERSION;
	/**
	 * Max number of entries in scoreList.
	 */
	public static int SCORELIST_ENTRIES_MAX;

	/**
	 * The pixel size of a tile.
	 */
	public static int TILE_SIZE;
	/**
	 * The width of tiles in the level.
	 */
	public static int LEVEL_WIDTH;
	/**
	 * The height of tiles in the level.
	 */
	public static int LEVEL_HEIGHT;
	/**
	 * The minimal speed.
	 */
	public static int SPEED_MIN;
	/**
	 * The maximal speed.
	 */
	public static int SPEED_MAX;
	/**
	 * The time a items despawns.
	 */
	public static int DESPAWNTIME;
	/**
	 * The minimal number of segments a snake must have.
	 */
	public static int MIN_SEGMENTS;
	/**
	 * Whether the Launcher is activated.
	 */
	public static boolean LAUNCHER;

	/**
	 * The size of a tile (BW).
	 */
	public static int TILE_SIZE_BW;
	/**
	 * Component width.
	 */
	public static int CONTENT_WIDTH;
	/**
	 * Component height.
	 */
	public static int CONTENT_HEIGHT;

	/**
	 * Loads the Constants.
	 */
	public static void load() {
		boolean error = false;

		props = new Properties();

		try {
			props.load(new FileReader(new File(INI)));

			DATAPATH = props.getProperty("datapath") + "/";
			VERSION = props.getProperty("version");
			SCORELIST_ENTRIES_MAX = Integer.parseInt(props.getProperty("scorelist_entries_max"));
			TILE_SIZE = Integer.parseInt(props.getProperty("tile_size"));
			LEVEL_WIDTH = Integer.parseInt(props.getProperty("level_width"));
			LEVEL_HEIGHT = Integer.parseInt(props.getProperty("level_height"));
			SPEED_MIN = Integer.parseInt(props.getProperty("speed_min"));
			SPEED_MAX = Integer.parseInt(props.getProperty("speed_max"));
			DESPAWNTIME = Integer.parseInt(props.getProperty("despawntime"));
			MIN_SEGMENTS = Integer.parseInt(props.getProperty("segments_min"));
			LAUNCHER = Boolean.parseBoolean(props.getProperty("launcher"));

			if (DATAPATH == null || VERSION == null)
				error = true;

		} catch (Exception e) {
			error = true;
		}

		if (error) {
			System.err.println(STR_ERROR);
			System.exit(1);
		}
	}

	/**
	 * Calculates other Constants.
	 */
	public static void calc() {
		TILE_SIZE_BW = (int) (Math.log(TILE_SIZE) / Math.log(2));
		CONTENT_WIDTH = LEVEL_WIDTH * TILE_SIZE;
		CONTENT_HEIGHT = LEVEL_HEIGHT * TILE_SIZE;
	}

	/**
	 * Save the Constants.
	 */
	public static void save() {
		try {
			props.setProperty("datapath", DATAPATH.replaceAll("/", ""));
			props.setProperty("version", VERSION);
			props.setProperty("scorelist_entries_max", SCORELIST_ENTRIES_MAX + "");
			props.setProperty("tile_size", TILE_SIZE + "");
			props.setProperty("level_width", LEVEL_WIDTH + "");
			props.setProperty("level_height", LEVEL_HEIGHT + "");
			props.setProperty("speed_min", SPEED_MIN + "");
			props.setProperty("speed_max", SPEED_MAX + "");
			props.setProperty("despawntime", DESPAWNTIME + "");
			props.setProperty("segments_min", MIN_SEGMENTS + "");
			props.setProperty("launcher", LAUNCHER + "");

			props.store(new FileOutputStream(new File(INI)), STR_COMMENT);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}