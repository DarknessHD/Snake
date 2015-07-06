package control;

/**
 * @author Stefan Kameter
 * @version 29.06.2015
 */
public enum Comp {
	/**
	 * Refers to the GameMenuPanel
	 */
	GAMEMENUPANEL,
	/**
	 * Refers to the ScoreListPanel
	 */
	SCORELISTPANEL,
	/**
	 * Refers to the GamePanel.
	 */
	GAMEPANEL;

	/**
	 * Returns the index of Comp.
	 * 
	 * @return the index
	 */
	public int getIndex() {
		switch (this) {
		case GAMEMENUPANEL:
			return 0;
		case SCORELISTPANEL:
			return 1;
		case GAMEPANEL:
			return 2;
		default:
			return -1;
		}
	}
}