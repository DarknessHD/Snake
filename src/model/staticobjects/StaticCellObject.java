package model.staticobjects;

import java.awt.Point;

import model.CellObject;

/**
 * @author Stefan Kameter
 * @version 05.07.2015
 */
public abstract class StaticCellObject extends CellObject { // TODO if not favoured, then list-index.
	private int id;

	/**
	 * Creates an instance of StaticCellObject.
	 * 
	 * @param id
	 *            StaticCellObject-ID
	 * @param image
	 *            the image of the CellObject
	 * @param position
	 *            the position of the CellObject
	 */
	public StaticCellObject(int id, String image, Point position) {
		super(image, position);
		this.id = id;
	}

	/**
	 * The id is necessary to load from level-files.
	 * 
	 * @return the id of the StaticCellObject
	 */
	public int getId() {
		return id;
	}
}