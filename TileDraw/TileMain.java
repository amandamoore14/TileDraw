/*
 * Name: Diane Mueller
 * Date: February 25, 2015
 * Course: CSC 212
 * Program: Assignment Ch10
 * 
 * Purpose:  Tile Main creates a panel of random rectangular tiles
 *           and allows the user to manipulate the tiles in the following ways.
 *           Draw tiles button draws tiles each at a random location, with a random size
 *           and a random color.
 *           Clear button clears the tiles from the drawing panel
 *           Sort button orders the tiles from largest area on the bottom to smallest
 *           area on the top.
 *           Left click moves the "top most" tile containing the click to the top.
 *           Shift Left click moves the "top most" tile containing the click to the bottom.
 *           Right click removes the "top most" tile containing the click from the panel.
 *           Shift right click removes all tiles containing the click
 *           Typing N on the keyboard adds a new randomly generated tile
 *           Typing S shuffles the tiles to random locations.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class TileMain extends MouseInputAdapter implements ActionListener,
		KeyListener {

	// constants for the drawing panel size, tile sizes, and # of tiles
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	public static final int MIN_SIZE = 50;
	public static final int MAX_SIZE = 150;
	public static final int TILES = 20;

	// constants used to generate random colors avoiding the darkest
	private static final int NUM_COLORS = 206;
	private static final int COLOR_OFFSET = 50;

	private JFrame frame;
	private JPanel drawingPanel;
	private JButton drawButton;
	private JButton clearButton;
	private JButton sortButton;

	private TileManager tiles;

	public static void main(String[] args) {
		TileMain gui = new TileMain();
	}

	public TileMain() {
		// set up components
		tiles = new TileManager();
		drawButton = new JButton("Draw Tiles");
		clearButton = new JButton("Clear");
		sortButton = new JButton("Sort");

		// set up panel for buttons
		JPanel buttons = new JPanel(new GridLayout(1, 2));
		buttons.add(drawButton);
		buttons.add(clearButton);
		buttons.add(sortButton);
		
		// set up panel for drawing tiles
		drawingPanel = new JPanel();
		drawingPanel.setBackground(Color.WHITE);

		// listen for events
		drawingPanel.addMouseListener(this);
		drawingPanel.addKeyListener(this);
		drawButton.addActionListener(this);
		clearButton.addActionListener(this);
		sortButton.addActionListener(this);

		// overall frame
		frame = new JFrame("Playing with Tiles");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(drawingPanel, BorderLayout.CENTER);
		frame.add(buttons, BorderLayout.SOUTH);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);

		// set drawingPanel to be focus for keyboard events
		drawingPanel.requestFocusInWindow();

	}

	// reacts to the draw button drawing random tiles
	// and the clear button which clears the panel.
	// The tiles are stored in z-order in an array list.
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		Graphics g = drawingPanel.getGraphics();
		if (source == drawButton) {
			for (int i = 0; i < TILES; i++) {
				Tile tile = makeRandomTile();
				tiles.addTile(tile);
			}
			tiles.drawAll(g);
		} else if (source == sortButton) {
			tiles.sort();
			clearPanel(g);
			tiles.drawAll(g);
		} else {
			clearPanel(g);
			tiles.clear();
		}

		// set drawingPanel to be focus for keyboard events
		drawingPanel.requestFocusInWindow();

	}

	// reacts to left/right mouse clicks and
	// shift-left/shift-right mouse clicks
	// left click moves the tile to the top of the z-order.
	// right click removes the tile from the panel.
	// shift-left moves the tile to the bottom of the z-order.
	// shift-right removes all tiles containing the click point.
	public void mouseClicked(MouseEvent event) {
		int clickX = event.getX();
		int clickY = event.getY();
		if (event.getButton() == 1) {
			if (event.isShiftDown()) {
				tiles.lower(clickX, clickY);
			} else {
				tiles.raise(clickX, clickY);
			}
		} else {
			if (event.isShiftDown()) {
				tiles.deleteAll(clickX, clickY);
			} else {
				tiles.delete(clickX, clickY);
			}
		}
		Graphics g = drawingPanel.getGraphics();
		clearPanel(g);
		tiles.drawAll(g);
	}

	// Responds to Keyboard events
	// N key causes a random tile to be added to the panel
	// S key causes the tiles to be redrawn at random locations
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		if (code == KeyEvent.VK_N) {
			Tile newTile = makeRandomTile();
			tiles.addTile(newTile);
			Graphics g = drawingPanel.getGraphics();
			newTile.draw(g);
		} else if (code == KeyEvent.VK_S) {
			int panelWidth = drawingPanel.getWidth();
			int panelHeight = drawingPanel.getHeight();
			tiles.shuffle(panelWidth, panelHeight);
			Graphics g = drawingPanel.getGraphics();
			clearPanel(g);
			tiles.drawAll(g);
		}
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {
	}

	// Creates and returns a random tile to be placed into the tile manager.
	public Tile makeRandomTile() {
		// choose random coordinates
		Random rand = new Random();
		int width = rand.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
		int height = rand.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
		int panelWidth = drawingPanel.getWidth();
		int panelHeight = drawingPanel.getHeight();
		int x = rand.nextInt(panelWidth - width);
		int y = rand.nextInt(panelHeight - height);

		// choose random color (avoid very darkest range of palette)
		int red = rand.nextInt(NUM_COLORS) + COLOR_OFFSET;
		int green = rand.nextInt(NUM_COLORS) + COLOR_OFFSET;
		int blue = rand.nextInt(NUM_COLORS) + COLOR_OFFSET;
		Color color = new Color(red, green, blue);

		// add random tile to manager
		Tile tile = new Tile(x, y, width, height, color);
		return tile;
	}

	// "clears" the drawing panel
	public void clearPanel(Graphics g) {
		Rectangle rect = frame.getBounds();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, rect.width, rect.height);
	}

}
