// Authors: Jonathon Kastner and Gavin Webster
// BoardCell Class

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;

public class BoardCell {

	// member variables for row and column
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDir;
	private boolean containsName = false;
	// constants determining the cell size in pixels when displayed by the board
	private static int BOX_WIDTH = 20;
	private static int BOX_HEIGHT = 20;
	private static int DOOR_THICKNESS = 3;
	
	// constructor setting row and column to integer parameters, and initial to z
	public BoardCell(int x, int y, char z) {
		row = x;
		column = y;
		initial = z;
		// This constructor is for non-door cells, so their door direction is NONE
		doorDir = DoorDirection.NONE;
	}
	
	// constructor specifically for cells that have doors
	public BoardCell(int x, int y, char z, DoorDirection d, boolean b) {
		row = x;
		column = y;
		initial = z;
		doorDir = d;
		containsName = b;
	}
	
	// isWalkway check method
	public boolean isWalkway() {
		if (this.initial == 'W') {
			return true;
		}
		else {
			return false;
		}
	}
	
	// isRoom check method
	public boolean isRoom() {
		if ((this.initial != 'W') && (this.initial != 'X')) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// isDoorway check method
	public boolean isDoorway() {
		if (this.doorDir != DoorDirection.NONE) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// draw method draws the cell as a rectangle, fills if it is a room, and determines whether or not it should display the room name
	 public void draw(Graphics2D g) {
		 // if the cell is part of a room or the closet, gray rectangle
		 if (isRoom() || initial == 'X') {
			 g.setColor(Color.LIGHT_GRAY);
			 g.fillRect(column * BOX_WIDTH, row * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		 }
		 // if the cell is not a room or closet, yellow outlined rectangle
		 else {
			 g.setColor(Color.YELLOW);
			 g.fillRect(column * BOX_WIDTH, row * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
			 g.setColor(Color.BLACK);
			 g.drawRect(column * BOX_WIDTH, row * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		 }
		 // deal with doors, doors are blue rectangles on the edge of cells
		 g.setColor(Color.BLUE);
		 // if the door opens down
		 if (doorDir == DoorDirection.DOWN) {
			 g.fillRect(column * BOX_WIDTH, row * BOX_HEIGHT + (BOX_HEIGHT - DOOR_THICKNESS), BOX_WIDTH, DOOR_THICKNESS);
		 }
		// if the door opens up
		 else if (doorDir == DoorDirection.UP) {
			 g.fillRect(column * BOX_WIDTH, row * BOX_HEIGHT, BOX_WIDTH, DOOR_THICKNESS);
		 }
		// if the door opens left
		 else if (doorDir == DoorDirection.LEFT) {
			 g.fillRect(column * BOX_WIDTH, row * BOX_HEIGHT, DOOR_THICKNESS, BOX_HEIGHT);
		 }
		// if the door opens right
		 else if (doorDir == DoorDirection.RIGHT) {
			 g.fillRect(column * BOX_WIDTH + (BOX_WIDTH - DOOR_THICKNESS), row * BOX_HEIGHT, DOOR_THICKNESS, BOX_HEIGHT);
		 }
	 }
	 
	 // draw target method is called if the cell is one of the user's targets
	 public void drawTarget(Graphics2D g) {
		 // replace the yellow square with a cyan one
		 g.setColor(Color.CYAN);
		 g.fillRect(column * BOX_WIDTH, row * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		 g.setColor(Color.BLACK);
		 g.drawRect(column * BOX_WIDTH, row * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
	 }
	 
	 // drawName method draws the name on the cell if the cell has the character 'N'
	 public void drawName(Graphics2D g, Map<Character, String> m) {
		 // uses a boolean to check if the cell is '*N'
		 if (containsName) {
			 g.drawString(m.get(initial), column * BOX_WIDTH + DOOR_THICKNESS, row * BOX_HEIGHT);
		 }
	 }

	// getters for initial and door direction
	public Object getDoorDirection() {
		return doorDir;
	}
	public Object getInitial() {
		return initial;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return column;
	}
	public int getBoxWidth() {
		return BOX_WIDTH;
	}
	public int getBoxHeight() {
		return BOX_HEIGHT;
	}
}
