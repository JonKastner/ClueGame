// Authors: Jonathon Kastner and Gavin Webster
// IntBoard Class

package experiment;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {

	// member variables
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visitedCells;
	private Set<BoardCell> targetCells;
	private BoardCell[][] grid;
	
	// constructor for IntBoard
	public IntBoard() {
		// initialize map and grid
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		grid = new BoardCell[4][4];
		// populate the grid
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				grid[i][j] = new BoardCell(i,j);
			}
		}
		// calculate the adjacencies for the map
		calcAdjacencies();
	}
	
	// calcAdjacencies method
	public void calcAdjacencies() {
		// for each cell in the grid
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				// make a new set of cells
				Set<BoardCell> adjs = new HashSet<BoardCell>();
				// add the adjacent cells if they are inside of the grid-range
				if (i+1 <= 3) {
					adjs.add(grid[i+1][j]);
				}
				if (i-1 >= 0) {
					adjs.add(grid[i-1][j]);
				}
				if (j+1 <= 3) {
					adjs.add(grid[i][j+1]);
				}
				if (j-1 >= 0) {
					adjs.add(grid[i][j-1]);
				}
				// add the set to the map where the origin cell is the key
				adjMtx.put(grid[i][j], adjs);
			}
		}
	}
	
	// getter for the list of adjacent cells
	public Set<BoardCell> getAdjList(BoardCell c){
		return adjMtx.get(c);
	}
	
	// findAllTargets method
	public void findAllTargets(BoardCell startCell, int pathLength) {
		// for each cell in the startCell's adjacent list
		for (BoardCell a : adjMtx.get(startCell)) {
			// if you've already visited the cell, next iteration of loop
			if (visitedCells.contains(a)) {
				continue;
			}
			// otherwise, add cell to visited list. If the pathlength is 1, add the cell to the target list
			// if path length is not 1, call the function recursively with pathlength-1
			else {
				visitedCells.add(a);
				if (pathLength == 1) {
					targetCells.add(a);
				}
				else {
					findAllTargets(a, pathLength - 1);
				}
				// once done with the cell, remove it from the visited list
				visitedCells.remove(a);
			}
		}
	}
	
	// calcTargets method
	public void calcTargets(BoardCell startCell, int pathLength) {
		// initialize the visitedCells and targetCells sets
		visitedCells = new HashSet<BoardCell>();
		targetCells = new HashSet<BoardCell>();
		// add the start cell to the visited list
		visitedCells.add(startCell);
		// find all of the targets for the start cell
		findAllTargets(startCell, pathLength);
	}
	
	// getter for targetCells set
	public Set<BoardCell> getTargets(){
		return targetCells;
	}

	// getter for a specific cell on the grid
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}
}
