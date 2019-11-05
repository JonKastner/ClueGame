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
	
	public Solution createSuggestion() {
		return null;
	}
	
	public void setRecentRoom(char c) {
		recentRoom = c;
	}
}
