// Authors: Jonathon Kastner and Gavin Webster
// HumanPlayer Child Class

package clueGame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

public class HumanPlayer extends Player {
	
	// constructor calling the parent constructor
	public HumanPlayer(String s, int r, int c, Color clr){
		super(s, r, c, clr);
		super.setSelectedStatus(false);
	}

	// overridden makeMove method, not used by the human player, but skeleton function is required
	@Override
	public void makeMove(Board board, GameControlGUI gui) {
		return;
	}
}
