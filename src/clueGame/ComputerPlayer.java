// Authors: Jonathon Kastner and Gavin Webster
// ComputerPlayer Child Class

package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char recentRoom;
	
	// Constructor calling the parent constructor
	public ComputerPlayer(String s, int r, int c, Color clr){
		super(s, r, c, clr);
		recentRoom = ' ';
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccustion() {
		
	}
	
	public void createSuggestion() {
		
	}
	
	public void setRecentRoom(char c) {
		recentRoom = c;
	}
}
