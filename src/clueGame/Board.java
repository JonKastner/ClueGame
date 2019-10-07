// Jonathon Kastner and Gavin Webster
// Board Class

package clueGame;

import java.util.Map;
import java.util.Set;

public class Board {

	// initialize member variables
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
		
	// initialize method
	public void initialize() {
		
	}
	
	// loadRoomConfig method
	public void loadRoomConfig() {
		
	}
	
	// loadBoardConfig method
	public void loadBoardConfig() {
		
	}
	
	// calcAdjacencies method
	public void calcAdjacencies() {
		
	}
	
	// calcTargets method
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	
	// setters and getters
	public void setConfigFiles(String boardConfigFile, String roomConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
	}
	public Map<Character, String> getLegend() {
		return legend;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public BoardCell getCellAt(int i, int j) {
		return null;
	}
	
	
}
