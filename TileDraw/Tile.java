
/*
 * Name: Amanda Moore
 * Date: 2/8/16
 * Course: CSC212
 * Program: ProgramCh10
 * 
 * Description:  Data type to describe a tile (rectangle) 
 * 
 *
 */

import java.awt.*;

public class Tile implements Comparable<Tile> {
	// data fields
	private int x;
	private int y;
	private int width;
	private int height;
	private Color tileColor;

	// constructor
	public Tile(int x, int y, int width, int height, Color tileColor) {
		setX(x);
		setY(y);
		this.width = width;
		this.height = height;
		this.tileColor = tileColor;
	}

	// returns the x coordinate of the upper left corner of the tile
	public int getX() {
		return x;
	}

	// returns the y coordinate of the upper left corner of the tile
	public int getY() {
		return y;
	}

	// returns the width of the tile
	public int getWidth() {
		return width;
	}

	// returns the height of the tile
	public int getHeight() {
		return height;
	}

	// returns the color of the tile
	public Color getColor() {
		return tileColor;
	}

	// sets the upper left corner's x coordinate
	public void setX(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("x value is negative");
		}
		this.x = x;

	}

	// sets the upper left corner's y coordinate
	public void setY(int y) {
		if (y < 0) {
			throw new IllegalArgumentException("y value is negative");
		}
		this.y = y;
	}

	// method containsPoint determines whether a point is within a tile
	// int pointY -- the x coordinate of a point
	// int pointY -- the y coordinate of a point
	// returns true if the rectangle contains the point, false otherwise
	public boolean containsPoint(int pointX, int pointY) {
		if (pointX <= x + width && pointY <= y + height && pointX >= x && pointY >= y) {
			return true;
		} else {
			return false;
		}
	}

	// method comparesTo compares two rectangular tiles based on their areas
	// returns a negative number the implicit parameter is less than the
	// explicit parameter, zero if the parameters are equal, and a positive
	// number if the implicit parameter is greater than the explicit parameter
	public int compareTo(Tile tile) {
		if (height * width < tile.getHeight() * tile.getWidth()) {
			return -1;
		} else if (height * width > tile.getHeight() * tile.getWidth()) {
			return 1;
		} else {
			return 0;
		}
	}

	// Draws this tile using the given graphics pen.
	public void draw(Graphics g) {
		g.setColor(tileColor);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}

	// String representation of the tile
	public String toString() {
		return "[ x = " + x + "  y = " + y + "  width = " + width + "  height = " + height + "]";
	}

}