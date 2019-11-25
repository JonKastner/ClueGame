// Authors: Jonathon Kastner and Gavin Webster
// ComputerPlayer Child Class

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char recentRoom;
	private boolean shouldMakeAccusation;
	private Solution accusationHolder;
	
	// Constructor calling the parent constructor
	public ComputerPlayer(String s, int r, int c, Color clr){
		super(s, r, c, clr);
		recentRoom = ' ';
		shouldMakeAccusation = false;
	}
	
	// pick location for the computer player
	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList targetList = new ArrayList<BoardCell>(targets);
		BoardCell target = null;
		// for every cell in the target list
		for (BoardCell c : targets) {
			// if the cell is an unvisited room, enter it
			if ((c.isRoom()) && (!c.getInitial().equals(recentRoom))) {
				target = c;
				setRoomInitial((char) c.getInitial());
				setRecentRoom((char) c.getInitial());
				return target;
			}
		}
		// otherwise, pick from random
		Random rand = new Random();
		int index = Math.abs(rand.nextInt() % targets.size());
		target = (BoardCell) targetList.get(index);
		return target;
	}
	
	// handles how the computer player makes an accusation
	public Solution makeAccusation() {
		// return the accusation variable
		return accusationHolder;
	}
	
	// create Suggestion method for the computer player
	public Solution createSuggestion(ArrayList<Card> People, ArrayList<Card> Weapons) {
		Solution suggestion = new Solution();
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		ArrayList<Card> unseenPeople = new ArrayList<Card>();
		// put every unseen weapon into a list and every unseen person into a list
		for (Card c : Weapons) {
			if (!getSeenCards().contains(c)) {
				unseenWeapons.add(c);
			}
		}
		for (Card c : People) {
			if (!getSeenCards().contains(c)) {
				unseenPeople.add(c);
			}
		}
		// if the unseen weapon list has one card, add it to the suggestion
		if (unseenWeapons.size() == 1) {
			suggestion.weapon = unseenWeapons.get(0).getName();
		}
		// if the unseen weapon list has more than one card, choose at random
		else if (unseenWeapons.size() > 1) {
			Random rand = new Random();
			int randomNum = Math.abs(rand.nextInt() % unseenWeapons.size());
			suggestion.weapon = unseenWeapons.get(randomNum).getName();
		}
		// if the unseen person list has one card, add it to the suggestion
		if (unseenPeople.size() == 1) {
			suggestion.person = unseenPeople.get(0).getName();
		}
		// if the unseen person list has more than one card, choose at random
		else if (unseenPeople.size() > 1) {
			Random rand = new Random();
			int randomNum = Math.abs(rand.nextInt() % unseenPeople.size());
			suggestion.person = unseenPeople.get(randomNum).getName();
		}
		// now set the room to the player's current room
		char initial = getRoomInitial();
		switch (initial) {
		case 'O':
			suggestion.room = "Office";
			break;
		case 'L':
			suggestion.room = "Living Room";
			break;
		case 'K':
			suggestion.room = "Kitchen";
			break;
		case 'D':
			suggestion.room = "Dining Room";
			break;
		case 'B':
			suggestion.room = "Bathroom";
			break;
		case 'E':
			suggestion.room = "Bedroom";
			break;
		case 'H':
			suggestion.room = "Hall";
			break;
		case 'T':
			suggestion.room = "Theatre";
			break;
		case 'C':
			suggestion.room = "Conservatory";
			break;
		}
		return suggestion;
	}
	
	// overridden method for makeMove, used to move the computerplayer
	@Override
	public void makeMove(Board board, GameControlGUI gui) {
		// if the player should make an accusation, make and check that accusation
		if (shouldMakeAccusation == true) {
			board.checkAccusation(makeAccusation());
			// create the result dialog and set the values
			ResultDialog result = new ResultDialog();
			result.setPlayerLabel(super.getName());
			String str = accusationHolder.person + ", " + accusationHolder.room + ", " + accusationHolder.weapon;
			result.setAccusationLabel(str);
			// check and set the result of the accusation
			if (board.checkAccusation(accusationHolder) == true) {
				result.setResultLabel("Correct");
			}
			else {
				result.setResultLabel("Incorrect");
			}
			result.setVisible(true);
			// if the accusation is false, need to reset the boolean so that they don't keep making accusations
			shouldMakeAccusation = false;
			return;
		}
		// otherwise pick a location, and move the player to that location
		BoardCell c = pickLocation(board.getTargets());
		super.setLocation(c.getRow(), c.getCol());
		// if the new cell is a room, then make a suggestion
		if (c.isRoom()) {
			// make suggestion
			Solution suggestion = createSuggestion(board.getPlayerCards(), board.getWeaponCards());
			// update the guess field in the GUI
			String guess = suggestion.person + ", " + suggestion.room + ", " + suggestion.weapon;
			gui.setGuess(guess);
			// have the board handle the suggestion and update the result field
			// if the suggestion is not disproved:
			if (board.handleSuggestion(this, suggestion) == null) {
				boolean hasRoom = false;
				// check if the computer player's cards contain the suggestion's room
				for (Card card : super.getHand()) {
					if (card.getName().equals(suggestion.room)) {
						hasRoom = true;
					}
				}
				// if the player doesn't have the card for the room
				if (hasRoom == false) {
					// indicate that the suggestion is the next accusation
					shouldMakeAccusation = true;
					accusationHolder = suggestion;
					// update result display
					gui.setResult("Nobody Disproves");
				}
			}
			// if the suggestion is disproved, indicate that it was disproved
			else {
				gui.setResult("Disproved by Player");
				// move the suggested player to the room
				for (Player p : board.getPeople()) {
					if (p.getName().equals(suggestion.person)) {
						p.setLocation(super.getRow(), super.getCol());
						break;
					}
				}
			}
		}
	}
	
	// setter for the recent room character
	public void setRecentRoom(char c) {
		recentRoom = c;
	}
}
