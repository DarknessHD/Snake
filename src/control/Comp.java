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
	 * Returns the convenient String of Comp.
	 * 
	 * @return the convenient String
	 */
	public String getString() {
		switch (this) {
		case GAMEMENUPANEL:
			return "GAMEMENUPANEL";
		case SCORELISTPANEL:
			return "SCORELISTPANEL";
		case GAMEPANEL:
			return "GAMEPANEL";
		default:
			return null;
		}
	}
}