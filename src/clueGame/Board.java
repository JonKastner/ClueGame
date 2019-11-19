// Jonathon Kastner and Gavin Webster
// Board Class

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{

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
	private Set<Card> dealtCards;
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	
	private Player currentPlayer = null;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		addMouseListener(this);
	}
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
	
	// loadRoomConfig method creates the legend and all of the room cards, adding them to the deck
	public void loadRoomConfig() throws BadConfigFormatException {
		cards = new HashSet<Card>();
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
				// create room card, add to deck
				if (arr[2].equals("Card")) {
					Card c = new Card(arr[1], CardType.ROOM);
					cards.add(c);
				}
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
				DoorDirection d = DoorDirection.NONE;
				boolean b = false;
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
				case 'N':
					b = true;
					break;
				}
				// if the cell has a doorway, use the second constructor
				board[numRows][i] = new BoardCell(numRows, i, arr[i].charAt(0), d, b);
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
					DoorDirection d = DoorDirection.NONE;
					boolean b = false;
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
					case 'N':
						b = true;
						break;
					}
					// if the cell has a doorway, use the second constructor
					board[numRows][i] = new BoardCell(numRows, i, arr[i].charAt(0), d, b);
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
	
	// loadConfigFiles method reads in the people and weapons files and create cards for them, adding them to the deck
	// Also adds all of the players to the players ArrayList
	public void loadConfigFiles() {
		// Initialize empty sets and File objects
		//cards = new HashSet<Card>();
		players = new ArrayList<Player>();
		//weapons = new HashSet<String>();
		dealtCards = new HashSet<Card>();
		String line = "";
		String[] arr;
		File pFile = new File("CluePeople.txt");
		File wFile = new File("ClueWeapons.txt");
		// Load Files, Create cards, Add cards to deck
		// try-catch for FileNotFound Exceptions
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
				// create the cards and add to the deck
				Card c = new Card(arr[0], CardType.PERSON);
				cards.add(c);
			}
			// Next deal with weapons, create cards and add to the deck
			scan = new Scanner(wFile);
			while (scan.hasNextLine()) {
				line = scan.nextLine();
				Card c = new Card(line, CardType.WEAPON);
				cards.add(c);
			}
			// full deck of cards has been created, copy into the deck arrayList for dealing
			deck = new ArrayList<Card>();
			for (Card c : cards) {
				deck.add(c);
			}
			// Call selectAnswer method to pick the solution from the deck of cards
			selectAnswer();
			// Deal the remaining cards randomly to each player (3 cards per player)
			for (int i = 0; i < 3; i++) {
				for (Player p : players) {
					Random rand = new Random();
					int index = Math.abs(rand.nextInt() % deck.size());
					Card c = deck.get(index);
					while (dealtCards.contains(c)) {
						index = Math.abs(rand.nextInt() % deck.size());
						c = deck.get(index);
					}
					// give card to player
					p.addCard(c);
					p.addSeen(c);
					// remove card from dealing deck
					// add card to the set of dealt cards
					dealtCards.add(c);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("One or more input files do not exist!");
		}
	}
	
	// String -> Color converter provided in assignment PDF
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

	
	// selectAnswer method picks 3 cards for the solution before dealing the other cards to players
	public void selectAnswer() {
		theAnswer = new Solution();
		
		// Choose a room card at random, add it to the solution
		ArrayList<Card> subDeck = new ArrayList<Card>(getRoomCards());
		Random rand = new Random();
		int randomNum = Math.abs(rand.nextInt() % subDeck.size());
		Card c = subDeck.get(randomNum);
		theAnswer.room = c.getName();
		// also remove from dealing deck and add to dealt cards
		dealtCards.add(c);
		
		// choose a person card at random, add it to the solution
		subDeck = new ArrayList<Card>(getPlayerCards());
		randomNum = Math.abs(rand.nextInt() % subDeck.size());
		c = subDeck.get(randomNum);
		theAnswer.person = c.getName();
		dealtCards.add(c);
		
		// choose a weapon card at random, add it to the solution
		subDeck = new ArrayList<Card>(getWeaponCards());
		randomNum = Math.abs(rand.nextInt() % subDeck.size());
		c = subDeck.get(randomNum);
		theAnswer.weapon = c.getName();
		dealtCards.add(c);
	}
	
	// handleSuggestion method determines how the board responds to suggestions
	public Card handleSuggestion(Player accuser, Solution suggestion) {
		// set the accuser index to the index of the accusing player in the player list
		int accuserIndex = players.indexOf(accuser);
		// set the current index to the index of the player after the accuser
		int currentIndex = players.indexOf(accuser) + 1;
		Player player = null;
		// loop through, iterating the current index until it reaches the accuser index (meaning that nobody can disprove except maybe the accuser)
		while (currentIndex != accuserIndex) {
			// if the index goes out of range, reset it to 0
			if (currentIndex >= players.size()) {
				currentIndex = 0;
				continue;
			}
			// retrieve the player at the index (the next player in the list)
			player = players.get(currentIndex);
			// get any cards that match the suggestion from the player
			ArrayList<Card> matchingCards = new ArrayList<Card>();
			for (Card c : player.getHand()) {
				if (c.getName() == suggestion.person || c.getName() == suggestion.room || c.getName() == suggestion.weapon) {
					matchingCards.add(c);
				}
			}
			// now check the matching cards
			// if there is one matching card, return it
			if (matchingCards.size() == 1) {
				//matchingCards.get(0).setSeen(true);
				accuser.addSeen(matchingCards.get(0));
				return matchingCards.get(0);
			}
			// if there are no matching cards, move on to the next player
			else if (matchingCards.size() == 0) {
				currentIndex++;
			}
			// if there are multiple matching cards, return a random one
			else {
				Random rand = new Random();
				int randomNum = Math.abs(rand.nextInt() % matchingCards.size());
				//matchingCards.get(randomNum).setSeen(true);
				accuser.addSeen(matchingCards.get(randomNum));
				return matchingCards.get(randomNum);
			}
		}
		return null;
	}
	
	// checkAccusation method checks if the accusation is correct
	public boolean checkAccusation(Solution accusation) {
		if ((theAnswer.person.equals(accusation.person)) && (theAnswer.room.equals(accusation.room)) && (theAnswer.weapon.equals(accusation.weapon))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// paintComponent method overrides the parent method to draw the gameboard
	@Override
	public void paintComponent(Graphics g) {
		// call the parent method
		super.paintComponent(g);
		// convert Graphics object to Graphics2D object
		Graphics2D g2d = (Graphics2D)g;
		// go through each boardcell and have them draw themselves
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				BoardCell cell = getCellAt(r, c);
				cell.draw(g2d);
			}
		}
		// go through each cell again and if applicable, draw the room names
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				BoardCell cell = getCellAt(r, c);
				cell.drawName(g2d, legend);
			}
		}
		// draw all of the players
		for (Player p : players) {
			p.draw(g2d);
		}
	}
	
	// methods for the MouseListener object, we only use clicked
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	@Override
	public void mouseClicked(MouseEvent event) {
		// only the human player should interact with the board
		if (currentPlayer instanceof HumanPlayer) {
			// get the x and y placement of the mouse click
			int x = event.getPoint().x;
			int y = event.getPoint().y;
			boolean check = false;
			// loop through every cell in targets
			for (BoardCell cell : targets) {
				// find the cell that the click was inside of
				if ((x > cell.getCol() * cell.getBoxWidth()) && (x < cell.getCol() * cell.getBoxWidth() + cell.getBoxWidth())) {
					if ((y > cell.getRow() * cell.getBoxHeight()) && (y < cell.getRow() * cell.getBoxHeight() + cell.getBoxHeight())) {
						// set the player's location to that cell
						currentPlayer.setLocation(cell.getRow(), cell.getCol());
						check = true;
						// indicate that the player has selected a target
						currentPlayer.setSelectedStatus(true);
						// update the board display
						repaint();
					}
				}
			}
			// show error message if an invalid target is selected
			if (check == false) {
				JOptionPane error = new JOptionPane();
				error.showMessageDialog(this, "Error, invalid target selected!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	// sets the current player
	public void setCurrentPlayer() {
		// if the current player is null (when the game first starts), set it to the human player
		if (currentPlayer == null) {
			for (Player p : players) {
				if (p instanceof HumanPlayer) {
					currentPlayer = p;
					break;
				}
			}
		}
		// otherwise, make the current player the next player in the list
		else {
			int index = players.indexOf(currentPlayer);
			if (index == players.size() - 1) {
				currentPlayer = players.get(0);
			}
			else {
				currentPlayer = players.get(index + 1);
			}
		}
	}
	
	// method containing the code ran when clicking the next player button
	public void nextPlayerClicked(GameControlGUI gui) {
		// if the current player is the user and they haven't selected a target, return
		if (currentPlayer instanceof HumanPlayer) {
			if (currentPlayer.getSelectedStatus() == false) {
				return;
			}
			else {
				// if the current player is a human player and they selected a target, reset the status to false before changing player
				currentPlayer.setSelectedStatus(false);
			}
		}
		// move to the next player
		setCurrentPlayer();
		// roll die
		Random rand = new Random();
		int roll = rand.nextInt((6 - 1) + 1) + 1;
		calcTargets(currentPlayer.getRow(), currentPlayer.getCol(), roll);
		// update game control
		gui.setTurn(currentPlayer.getName());
		gui.setRoll(roll);
		gui.setGuess("");
		gui.setResult("");
		// if the current player is the user, highlight targets
		if (currentPlayer instanceof HumanPlayer) {
			// highlight targets
			Graphics g = getGraphics();
			Graphics2D g2d = (Graphics2D)g;
			for (BoardCell cell : getTargets()) {
				cell.drawTarget(g2d);
			}
		}
		// if the current player is a computer, make their move and update the board
		else {
			currentPlayer.makeMove(this);
			repaint();
		}
	}
	public void makeAccusationClicked() {
		
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
	public Set<Card> getCards(){
		return cards;
	}
	public ArrayList<Card> getPlayerCards(){
		ArrayList<Card> result = new ArrayList<Card>();
		for (Card c : cards) {
			if (c.getType() == CardType.PERSON) {
				result.add(c);
			}
		}
		return result;
	}
	public ArrayList<Card> getWeaponCards(){
		ArrayList<Card> result = new ArrayList<Card>();
		for (Card c : cards) {
			if (c.getType() == CardType.WEAPON) {
				result.add(c);
			}
		}
		return result;
	}
	public ArrayList<Card> getRoomCards(){
		ArrayList<Card> result = new ArrayList<Card>();
		for (Card c : cards) {
			if (c.getType() == CardType.ROOM) {
				result.add(c);
			}
		}
		return result;
	}
	public Set<Card> getDealtCards(){
		return dealtCards;
	}
	public Solution getAnswer() {
		return theAnswer;
	}
	public void setAnswer(String p, String r, String w) {
		theAnswer.person = p;
		theAnswer.room = r;
		theAnswer.weapon = w;
	}
}
