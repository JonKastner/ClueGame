// Jonathon Kastner and Gavin Webster
// Board Class

package clueGame;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

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
	private Set<BoardCell> visited;
	private Solution theAnswer;
	private Set<Card> cards;
	private ArrayList<Player> players;
	private Set<String> weapons;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
		
	// initialize method performs the loadRoomConfig and loadBoardConfig methods. Then it populates adjMatrix
	public void initialize() {
		// try to load the files and calculate adjacencies, catch format exceptions
		try {
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
		} catch (BadConfigFormatException e) {
			System.out.println("File Configuration Error!");
		}
	}
	
	// loadRoomConfig method
	public void loadRoomConfig() throws BadConfigFormatException {
		legend = new HashMap<Character, String>();
		File in = new File(roomConfigFile);
		// try to open the file
		try {
			Scanner scan = new Scanner(in);
			String line = "";
			// get each line, split it up, trim off leading/trailing whitespace
			while(scan.hasNextLine()) {
				line = scan.nextLine();
				String[] arr = line.split(",", 0);
				for (int i = 0; i < arr.length; i++) {
					arr[i] = arr[i].trim();
				}
				if ((arr.length > 3) || (arr.length == 0)) {
					throw new BadConfigFormatException("Error: Incorrect legend file format!");
				}
				else if ((!arr[2].equals("Card")) && (!arr[2].equals("Other"))){
					throw new BadConfigFormatException("Error: Incorrect legend file format!");
				}
				// add to legend map
				legend.put(arr[0].charAt(0), arr[1]);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist!");
		}
	}
	
	// loadBoardConfig method
	public void loadBoardConfig() throws BadConfigFormatException {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		numRows = 0;
		File in = new File(boardConfigFile);
		Scanner scan = null;
		// try to open the config file
		try {
			scan = new Scanner(in);
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist!");
		}
		String line = "";
		// get the first line, split by commas, set the number of columns
		line = scan.nextLine();
		String[] arr = line.split(",", 0);
		for (int i = 0; i < arr.length; i++) {
			// if the input character isn't a room initial, throw an exception
			if (!legend.containsKey(arr[i].charAt(0))) {
				throw new BadConfigFormatException("Error: Detected room initial not in legend file!");
			}
		}
		numColumns = arr.length;
		// add the boardcells to the 2D array
		for (int i = 0; i < numColumns; i++) {
			if (arr[i].length() > 1) {
				DoorDirection d;
				// switch case to set the door direction
				switch(arr[i].charAt(1)) {
				case 'D':
					d = DoorDirection.DOWN;
					break;
				case 'U':
					d = DoorDirection.UP;
					break;
				case 'R':
					d = DoorDirection.RIGHT;
					break;
				case 'L':
					d = DoorDirection.LEFT;
					break;
				default:
					d = DoorDirection.NONE;
				}
				// if the cell has a doorway, use the second constructor
				board[numRows][i] = new BoardCell(numRows, i, arr[i].charAt(0), d);
			} 
			else {
				// otherwise use the basic constructor
				board[numRows][i] = new BoardCell(numRows, i, arr[i].charAt(0));
			}
		}
		numRows++;
		// do the same as above for the rest of the lines
		while(scan.hasNextLine()) {
			line = scan.nextLine();
			arr = line.split(",", 0);
			for (int i = 0; i < arr.length; i++) {
				if (!legend.containsKey(arr[i].charAt(0))) {
					throw new BadConfigFormatException("Error: Detected room initial not in legend file!");
				}
			}
			// if the array lengths don't equal the original number of columns, throw an exception
			if (arr.length != numColumns) {
				throw new BadConfigFormatException("Error: Number of columns not consistent in board file!");
			}
			for (int i = 0; i < numColumns; i++) {
				if (arr[i].length() > 1) {
					DoorDirection d;
					// switch case to set the door direction
					switch(arr[i].charAt(1)) {
					case 'D':
						d = DoorDirection.DOWN;
						break;
					case 'U':
						d = DoorDirection.UP;
						break;
					case 'R':
						d = DoorDirection.RIGHT;
						break;
					case 'L':
						d = DoorDirection.LEFT;
						break;
					default:
						d = DoorDirection.NONE;
					}
					// if the cell has a doorway, use the second constructor
					board[numRows][i] = new BoardCell(numRows, i, arr[i].charAt(0), d);
				} 
				else {
					// otherwise use the base constructor
					board[numRows][i] = new BoardCell(numRows, i, arr[i].charAt(0));
				}
			}
			// add 1 to numRows
			numRows++;
		}
	}
	
	// calcAdjacencies method
	public void calcAdjacencies() {
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				// make a new set of cells
				Set<BoardCell> adjs = new HashSet<BoardCell>();
				// add the adjacent cells if they are inside of the grid-range
				// If the current cell is a walkway
				if (board[i][j].isWalkway()) {
					if (i + 1 < numRows) {
						// Deal with cell below if it exists
						// If the cell is a walkway, add it to adjacency list regardless
						if (board[i+1][j].isWalkway()) {
							adjs.add(board[i+1][j]);
						}
						// If the cell is a room, check to make sure that it is a doorway
						else if (board[i+1][j].isRoom()) {
							if (board[i+1][j].isDoorway()) {
								// If the doorway opens up, add to adjacency list
								if (board[i+1][j].getDoorDirection() == DoorDirection.UP) {
									adjs.add(board[i+1][j]);
								}
							}
						}
					}
					if (i - 1 >= 0) {
						// Deal with cell above if it exists
						// If the cell is a walkway, add it to adjacency list regardless
						if (board[i-1][j].isWalkway()) {
							adjs.add(board[i-1][j]);
						}
						// If the cell is a room, check to make sure that it is a doorway
						else if (board[i-1][j].isRoom()) {
							if (board[i-1][j].isDoorway()) {
								// If the doorway opens down, add to adjacency list
								if (board[i-1][j].getDoorDirection() == DoorDirection.DOWN) {
									adjs.add(board[i-1][j]);
								}
							}
						}
					}
					if (j + 1 < numColumns) {
						// Deal with cell to the right if it exists
						// If the cell is a walkway, add it to adjacency list regardless
						if (board[i][j+1].isWalkway()) {
							adjs.add(board[i][j+1]);
						}
						// If the cell is a room, check to make sure that it is a doorway
						else if (board[i][j+1].isRoom()) {
							if (board[i][j+1].isDoorway()) {
								// If the doorway opens to the left, add to adjacency list
								if (board[i][j+1].getDoorDirection() == DoorDirection.LEFT) {
									adjs.add(board[i][j+1]);
								}
							}
						}
					}
					if (j - 1 >= 0) {
						// Deal with cell to the left if it exists
						// If the cell is a walkway, add it to adjacency list regardless
						if (board[i][j-1].isWalkway()) {
							adjs.add(board[i][j-1]);
						}
						// If the cell is a room, check to make sure that it is a doorway
						else if (board[i][j-1].isRoom()) {
							if (board[i][j-1].isDoorway()) {
								// If the doorway opens to the right, add to adjacency list
								if (board[i][j-1].getDoorDirection() == DoorDirection.RIGHT) {
									adjs.add(board[i][j-1]);
								}
							}
						}
					}
				}
				// If the current cell is inside of a room
				else if (board[i][j].isRoom()) {
					// The cell can only have adjacent cells if it is also a doorway
					if (board[i][j].isDoorway()) {
						// If the door is accessible from above, add the cell above
						if (board[i][j].getDoorDirection() == DoorDirection.UP) {
							adjs.add(board[i-1][j]);
						}
						// If the door is accessible from below, add the cell below
						else if (board[i][j].getDoorDirection() == DoorDirection.DOWN) {
							adjs.add(board[i+1][j]);
						}
						// If the cell is accessible from the left, add the cell to the left
						else if (board[i][j].getDoorDirection() == DoorDirection.LEFT) {
							adjs.add(board[i][j-1]);
						}
						// If the cell is accessible from the right, add the cell to the right
						else if (board[i][j].getDoorDirection() == DoorDirection.RIGHT) {
							adjs.add(board[i][j+1]);
						}
					}
				}
				// add the set to the map where the origin cell is the key
				adjMatrix.put(board[i][j], adjs);
			}
		}
	}
	
	// calcTargets method
	public void calcTargets(int row, int col, int pathLength) {
		// initialize the visitedCells and targetCells sets
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		// add the start cell to the visited list
		visited.add(board[row][col]);
		// find all of the targets for the start cell
		findAllTargets(board[row][col], pathLength);
	}
	
	// findAllTargets method
	public void findAllTargets(BoardCell startCell, int pathLength) {
		// for each cell in the startCell's adjacent list
		for (BoardCell a : adjMatrix.get(startCell)) {
			// if you've already visited the cell, next iteration of loop
			if (visited.contains(a)) {
				continue;
			}
			// otherwise, add cell to visited list. If the pathlength is 1, add the cell to the target list
			// if path length is not 1, call the function recursively with pathlength-1
			else {
				visited.add(a);
				if ((pathLength == 1)||(a.isDoorway())) {
					targets.add(a);
				}
				else {
					findAllTargets(a, pathLength - 1);
				}
				// once done with the cell, remove it from the visited list
				visited.remove(a);
			}
		}
	}
	
	public void loadConfigFiles() {
		// Initialize empty sets and File objects
		cards = new HashSet<Card>();
		players = new ArrayList<Player>();
		weapons = new HashSet<String>();
		String line = "";
		String[] arr;
		File pFile = new File("CluePeople.txt");
		File wFile = new File("ClueWeapons.txt");
		File rFile = new File("RoomLegend.txt");
		// Load Files, Create cards, Add cards to deck
		try {
			// Start with people (Players)
			Scanner scan = new Scanner(pFile);
			while (scan.hasNextLine()) {
				line = scan.nextLine();
				arr = line.split(", ", 0);
				// If the player is a human player
				if (arr[2].equals("Human")) {
					HumanPlayer temp = new HumanPlayer(arr[0], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), convertColor(arr[1]));
					players.add(temp);
				}
				// If the player is a computer player
				else if (arr[2].equals("Computer")) {
					ComputerPlayer temp = new ComputerPlayer(arr[0], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), convertColor(arr[1]));
					players.add(temp);
				}
			}
			// Next deal with weapons
			/*scan = new Scanner(wFile);
			while (scan.hasNextLine()) {
				//TODO
			}
			// Finally deal with Rooms
			scan = new Scanner(rFile);
			while (scan.hasNextLine()) {
				//TODO
			}
			// Call selectAnswer method to pick the solution from the deck of cards
			selectAnswer();*/
			// Deal the remaining cards randomly to each player (3 cards per player)
			//TODO this
		} catch (FileNotFoundException e) {
			System.out.println("One or more input files do not exist!");
		}
	}
	
	public Color convertColor(String strColor) {
		 Color color;
		 try {
		 // We can use reflection to convert the string to a color
		 Field field = Class.forName("java.awt.Color").getField(strColor.trim());
		 color = (Color)field.get(null);
		 } catch (Exception e) {
		 color = null; // Not defined
		 }
		 return color;
		}

	
	public void selectAnswer() {
		theAnswer = new Solution();
		//TODO NEED MORE HERE
		// Choose a room, person, and weapon card at random and assign to the solution
	}
	
	public Card handleSuggestion() {
		return null;
		//TODO
	}
	
	public boolean checkAccusation(Solution accusation) {
		return false;
		//TODO
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
		return board[i][j];
	}
	public Set<BoardCell> getAdjList(int i, int j){
		return adjMatrix.get(board[i][j]);
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	// Getters and Setters used for testing Player, Weapon, and Deck creation/loading
	public ArrayList<Player> getPeople(){
		return players;
	}
	public Set<String> getWeapons(){
		return weapons;
	}
	public Set<Card> getCards(){
		return cards;
	}
	
}
