// Authors: Jonathon Kastner and Gavin Webster
// ComputerPlayer Child Class

package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	// Constructor calling the parent constructor
	ComputerPlayer(String s, int r, int c, Color clr){
		super(s, r, c, clr);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccustion() {
		
	}
	
	public void createSuggestion() {
		
	}
}
