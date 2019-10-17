// Authors: Jonathon Kastner and Gavin Webster
// BoardCell Class

package clueGame;

public class BoardCell {

	// member variables for row and column
	private int row;
	private int column;
	private char initial;
	private char door = ' ';
	
	// constructor setting row and column to integer parameters
	public BoardCell(int x, int y, char z) {
		row = x;
		column = y;
		initial = z;
	}
	
	public BoardCell(int x, int y, char z, char d) {
		row = x;
		column = y;
		initial = z;
		door = d;
	}
	
	// isWalkway check method
	public boolean isWalkway() {
		return false;
	}
	
	// isRoom check method
	public boolean isRoom() {
		return false;
	}
	
	// isDoorway check method
	public boolean isDoorway() {
		if ((door != ' ') && (door != 'N')) {
			return true;
		} else {
			return false;
		}
	}

	// getters for initial and door direction
	public Object getDoorDirection() {
		if (door == 'U') {
			return DoorDirection.UP;
		}
		else if (door == 'L') {
			return DoorDirection.LEFT;
		}
		else if (door == 'R') {
			return DoorDirection.RIGHT;
		}
		else if (door == 'D') {
			return DoorDirection.DOWN;
		}
		else {
			return DoorDirection.NONE;
		}
	}

	public Object getInitial() {
		return initial;
	}
}
