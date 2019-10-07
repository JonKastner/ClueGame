// Authors: Jonathon Kastner and Gavin Webster
// BoardCell Class

package clueGame;

public class BoardCell {

	// member variables for row and column
	private int row;
	private int column;
	private char initial;
	
	// constructor setting row and column to integer parameters
	public BoardCell(int x, int y, char z) {
		row = x;
		column = y;
		initial = z;
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
		return false;
	}

	// getters for initial and door direction
	public Object getDoorDirection() {
		return null;
	}

	public Object getInitial() {
		return null;
	}
}
