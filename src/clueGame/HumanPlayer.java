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

	@Override
	public void makeMove(Board board) {
		return;
	}
	
	/*private class TargetListener implements MouseListener {
		
		public Set<BoardCell> set;
		public HumanPlayer player;
		
		TargetListener(Set<BoardCell> targets, HumanPlayer p){
			set = targets;
			player = p;
		}
		
		public void mousePressed(MouseEvent event) {}
		
		public void mouseReleased(MouseEvent event) {}
		
		public void mouseEntered(MouseEvent event) {}
		
		public void mouseExited(MouseEvent event) {}
		
		public void mouseClicked(MouseEvent event) {
			int x = event.getPoint().x;
			int y = event.getPoint().y;
			for (BoardCell cell : set) {
				if ((x > cell.getCol() * cell.getBoxWidth()) && (x < cell.getCol() * cell.getBoxWidth() + cell.getBoxWidth())) {
					if ((y > cell.getRow() * cell.getBoxHeight()) && (y < cell.getRow() * cell.getBoxHeight() + cell.getBoxHeight())) {
						player.setLocation(cell.getRow(), cell.getCol());
						break;
					}
				}
			}
		}
	}*/
}
