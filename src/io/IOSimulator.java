package io;

import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;

import model.Direction;
import model.Level;
import model.Snake;
import model.staticobjects.StaticCellObject;
import model.staticobjects.Wall;

@SuppressWarnings("javadoc")
public class IOSimulator {
	private static final Hashtable<String, Level> levels=new Hashtable<String, Level>();

	public static void init(){
		String name0 = "normal_endless_sp1_easy";
		Snake[] snakes0 = { new Snake(3, new Point(5, 3), Direction.RIGHT) };
		StaticCellObject[] staticCellObjects0 = new StaticCellObject[88];
		int id = 0;
		for (int y = 0; y < 20; y++) {
			if (y == 9 || y == 10)
				continue;
			staticCellObjects0[id] = new Wall(new Point(0, y));
			id++;
			staticCellObjects0[id] = new Wall(new Point(29, y));
			id++;
		}
		for (int x = 1; x < 29; x++) {
			if (x == 14 || x == 15)
				continue;
			staticCellObjects0[id] = new Wall(new Point(x, 0));
			id++;
			staticCellObjects0[id] = new Wall(new Point(x, 19));
			id++;
		}
		levels.put(name0, new Level(name0, 30, 20, true, 5, 3, snakes0, staticCellObjects0));

		String name1 = "normal_endless_sp2_easy";
		Snake[] snakes1 = { new Snake(3, new Point(5, 3), Direction.RIGHT),	new Snake(3, new Point(24, 3), Direction.LEFT) };
		snakes1[1].setPathfinder();
		levels.put(name1, new Level(name1, 30, 20, true, 5, 3, snakes1, staticCellObjects0));

		String name2 = "very_big_mp_hard";
		Snake[] snakes2 = { new Snake(3, new Point(2, 35), Direction.UP), new Snake(3, new Point(57, 35), Direction.UP) };
		StaticCellObject[] staticCellObjects2 = new StaticCellObject[364];
		id = 0;
		for (int y = 0; y < 40; y++) {
			staticCellObjects2[id] = new Wall(new Point(0, y));
			id++;
			staticCellObjects2[id] = new Wall(new Point(59, y));
			id++;
		}
		for (int x = 1; x < 59; x++) {
			staticCellObjects2[id] = new Wall(new Point(x, 0));
			id++;
			staticCellObjects2[id] = new Wall(new Point(x, 39));
			id++;
		}
		for (int y = 2; y < 38; y++)
			for (int x = 2; x < 58; x++)
				if (y % 3 == 0 && x % 4 == 0) {
					staticCellObjects2[id] = new Wall(new Point(x, y));
					id++;
				}
		levels.put(name2, new Level(name2, 60, 40, false, 5, 6, snakes2, staticCellObjects2));
	}

	public static Vector<String> getLevelNames() {
		Vector<String> vec = new Vector<String>();
		for (String name : levels.keySet())
			if (levels.get(name).isAllowed())
				vec.add(name);
		return vec;
	}

	public static Level getLevel(String name) {
		return levels.get(name).clone();
	}
}