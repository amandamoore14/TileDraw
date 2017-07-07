
/*
 * Name: Amanda Moore
 * Date: 2/10/16
 * Course: CSC212
 * Program: ProgramCh10
 * 
 * Description:  Data type to manage an array list of tiles
 * 
 * I discussed strategy with....
 *
 */

import java.util.*;
import java.awt.*;

public class TileManager {

	// data field
	private ArrayList<Tile> tiles;

	// constructor
	public TileManager() {
		tiles = new ArrayList<Tile>();
	}

	// adds a tile to the array list
	public void addTile(Tile rect) {
		tiles.add(rect);
	}

	// draws all tiles in the array list from beginning to end
	public void drawAll(Graphics g) {
		for (int i = 0; i < TileMain.TILES; i++) {
			tiles.get(i).draw(g);
		}
	}

	// moves the "top most" tile containing the point (x, y)
	// to the end of the array list
	public void raise(int x, int y) {
		int largestIndex = 0;
		for (int i = tiles.size() - 1; i >= 0; i--) {
			if (tiles.get(i).containsPoint(x, y) && i >= largestIndex) {
				Tile clickedTile = tiles.get(i);
				tiles.remove(i);
				tiles.add(clickedTile);
				largestIndex = i;
			}
		}
	}

	// moves the "top most" tile containing the point (x, y)
	// to the beginning of the array list
	public void lower(int x, int y) {
		int largestIndex = 0;
		for (int i = tiles.size() - 1; i >= 0; i--) {
			if (tiles.get(i).containsPoint(x, y) && i >= largestIndex) {
				Tile clickedTile = tiles.get(i);
				tiles.remove(i);
				tiles.add(0, clickedTile);
				largestIndex = i;
			}
		}
	}

	// removes the "top most" tile containing the point (x, y)
	// from the array list
	public void delete(int x, int y) {
		int largestIndex = 0;
		for (int i = tiles.size() - 1; i >= 0; i--) {
			if (tiles.get(i).containsPoint(x, y) && i >= largestIndex) {
				tiles.remove(i);
				largestIndex = i;
			}
		}
	}

	// removes all tiles containing the point (x, y)
	// from the array List
	public void deleteAll(int x, int y) {
		for (int i = tiles.size() - 1; i >= 0; i--) {
			if (tiles.get(i).containsPoint(x, y)) {
				tiles.remove(i);
			}
		}
	}

	// shuffles the tiles in the array list and assigns
	// each to a new random position within the panel
	// with dimension panelWidth, panelHeight
	public void shuffle(int panelWidth, int panelHeight) {
		Collections.shuffle(tiles);
		for (int i = tiles.size() - 1; i >= 0; i--) {
			Random randX = new Random();
			Random randY = new Random();
			int x = randX.nextInt(panelWidth - tiles.get(i).getWidth());
			int y = randY.nextInt(panelHeight - tiles.get(i).getHeight());
			tiles.get(i).setX(x);
			tiles.get(i).setY(y);

		}
	}

	// sorts the array List using the natural ordering from largest to smallest
	public void sort() {
		Collections.sort(tiles);
	}

	// clears the array List
	public void clear() {
		tiles.clear();
	}

}