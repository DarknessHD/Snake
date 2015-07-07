package control;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * @author Stefan Kameter
 * @version 05.07.2015
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
	public static final int SCORELISTENTRIES;

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

	static {
		Properties props = new Properties();
		try {
			props.load(new FileReader(new File("snake.ini")));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		DATAPATH = props.getProperty("datapath");
		VERSION = props.getProperty("version");
		SCORELISTENTRIES = Integer.parseInt(props.getProperty("scorelistentries"));
		TILE_SIZE = Integer.parseInt(props.getProperty("tile_size"));
		LEVEL_WIDTH = Integer.parseInt(props.getProperty("level_width"));
		LEVEL_HEIGHT = Integer.parseInt(props.getProperty("level_height"));
		MIN_SPEED = Integer.parseInt(props.getProperty("speed_min"));
		MAX_SPEED = Integer.parseInt(props.getProperty("speed_max"));

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
	public static final int CANVAS_WIDTH = LEVEL_WIDTH * TILE_SIZE + 1;
	/**
	 * Component height.
	 */
	public static final int CANVAS_HEIGHT = LEVEL_HEIGHT * TILE_SIZE + 1;
}