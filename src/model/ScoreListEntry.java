package model;

/**
 * @author Stefan Kameter
 * @version 08.07.2015
 */
public class ScoreListEntry implements Comparable<ScoreListEntry> {
	private final String level;
	private final String name;
	private final int score;

	/**
	 * Creates an instance of ScoreListEntry.
	 * 
	 * @param level
	 *            the level
	 * @param name
	 *            the name
	 * @param score
	 *            the score
	 */
	public ScoreListEntry(String level, String name, int score) {
		this.level = level;
		this.name = name;
		this.score = score;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(ScoreListEntry entry) {
		return level.compareTo(entry.getLevel());
	}
}