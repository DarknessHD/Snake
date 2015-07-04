package model;

/**
 * @author Stefan Kameter
 * @version 04.07.2015
 */
public class ScoreListEntry {
	private final String name;
	private final int score;

	/**
	 * Creates an instance of ScoreListEntry.
	 * 
	 * @param name
	 *            the name
	 * @param score
	 *            the score
	 */
	public ScoreListEntry(String name, int score) {
		this.name = name;
		this.score = score;
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
}