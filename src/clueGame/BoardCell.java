// Authors: Jonathon Kastner and Gavin Webster
// BoardCell Class

package clueGame;

public class BoardCell {

	// member variables for row and column
	private int row;
	private int column;
	private char initial;
	private DoorDirection door;
	
	// constructor setting row and column to integer parameters, and initial to z
	public BoardCell(int x, int y, char z) {
		row = x;
		column = y;
		initial = z;
		// This constructor is for non-door cells, so their door direction is NONE
		door = DoorDirection.NONE;
	}
	
	// constructor specifically for cells that have doors
	public BoardCell(int x, int y, char z, DoorDirection d) {
		row = x;
		column = y;
		initial = z;
		door = d;
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
		if (this.door != DoorDirection.NONE) {
			return true;
		}
		else {
			return false;
		}
	}

	// getters for initial and door direction
	public Object getDoorDirection() {
		return door;
	}
	public Object getInitial() {
		return initial;
	}
}
