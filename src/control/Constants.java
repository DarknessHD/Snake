package control;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class Constants {

	/**
	 * The path of data.
	 */
	public static final String DATAPATH;
	/**
	 * Game version.
	 */
	public static final String VERSION;
	/**
	 * Max number of entries in scoreList.
	 */
	public static final int SCORELIST_ENTRIES_MAX;

	/**
	 * The pixel size of a tile.
	 */
	public static final int TILE_SIZE;
	/**
	 * The width of tiles in the level.
	 */
	public static final int LEVEL_WIDTH;
	/**
	 * The height of tiles in the level.
	 */
	public static final int LEVEL_HEIGHT;
	/**
	 * The minimal speed.
	 */
	public static final int MIN_SPEED;
	/**
	 * The maximal speed.
	 */
	public static final int MAX_SPEED;
	/**
	 * The minimal number of segments a snake must have.
	 */
	public static final int MIN_SEGMENTS;

	static {
		Properties props = new Properties();
		try {
			props.load(new FileReader(new File("snake.ini")));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		DATAPATH = props.getProperty("datapath") + "/";
		VERSION = props.getProperty("version");
		SCORELIST_ENTRIES_MAX = Integer.parseInt(props.getProperty("scorelist_entries_max"));
		TILE_SIZE = Integer.parseInt(props.getProperty("tile_size"));
		LEVEL_WIDTH = Integer.parseInt(props.getProperty("level_width"));
		LEVEL_HEIGHT = Integer.parseInt(props.getProperty("level_height"));
		MIN_SPEED = Integer.parseInt(props.getProperty("speed_min"));
		MAX_SPEED = Integer.parseInt(props.getProperty("speed_max"));
		MIN_SEGMENTS = Integer.parseInt(props.getProperty("segments_min"));

		if (DATAPATH == null || VERSION == null)
			System.exit(1);
	}

	/**
	 * The size of a tile (BW).
	 */
	public static final int TILE_SIZE_BW = (int) (Math.log(TILE_SIZE) / Math.log(2));
	/**
	 * Component width.
	 */
	public static final int CANVAS_WIDTH = LEVEL_WIDTH * TILE_SIZE;
	/**
	 * Component height.
	 */
	public static final int CANVAS_HEIGHT = LEVEL_HEIGHT * TILE_SIZE;
}