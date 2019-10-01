package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visitedCells;
	private Set<BoardCell> targetCells;
	private BoardCell[][] grid;
	
	public IntBoard() {
		calcAdjacencies();
	}
	
	public void calcAdjacencies() {
		
	}
	
	public Set<BoardCell> getAdj(BoardCell c){
		return null;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
}
