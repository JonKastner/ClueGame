// Authors: Jonathon Kastner and Gavin Webster
// ComputerPlayer Child Class

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char recentRoom;
	
	// Constructor calling the parent constructor
	public ComputerPlayer(String s, int r, int c, Color clr){
		super(s, r, c, clr);
		recentRoom = ' ';
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList targetList = new ArrayList<BoardCell>(targets);
		BoardCell target = null;
		for (BoardCell c : targets) {
			if ((c.isRoom()) && (!c.getInitial().equals(recentRoom))) {
				target = c;
				setRoomInitial((char) c.getInitial());
				return target;
			}
		}
		Random rand = new Random();
		int index = Math.abs(rand.nextInt() % targets.size());
		target = (BoardCell) targetList.get(index);
		return target;
	}
	
	public Solution makeAccusation() {
		return null;
	}
	
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
	
	public void setRecentRoom(char c) {
		recentRoom = c;
	}
}
