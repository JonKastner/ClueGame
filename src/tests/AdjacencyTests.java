// Authors: Jonathon Kastner and Gavin Webster
// Adjacency JUnit Test class

package tests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.*;

public class AdjacencyTests {

	// Before method to set up the board
	private static Board board;
	@Before
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueMap.csv", "RoomLegend.txt");		
		board.initialize();
	}
	
	// Test the adjacencies for cells inside of various rooms
	@Test
	public void testAdjacencyInsideRooms() {
		Set<BoardCell> list = new HashSet<BoardCell>();
		
		// corner
		list = board.getAdjList(23, 23);
		assertEquals(0, list.size());
		
		// walkway to the left
		list = board.getAdjList(1, 5);
		assertEquals(0, list.size());
		
		// walkway below
		list = board.getAdjList(7, 13);
		assertEquals(0, list.size());
		
		// walkway above
		list = board.getAdjList(18, 4);
		assertEquals(0, list.size());
		
		//walkway to the right
		list = board.getAdjList(12, 4);
		assertEquals(0, list.size());
		
		// middle of room
		list = board.getAdjList(14, 22);
		assertEquals(0, list.size());
	}
	
	// Test adjacencies for cells that are doorways
	@Test
	public void TestRoomExitAdjacency() {
		Set<BoardCell> list = new HashSet<BoardCell>();
		
		// cell with door direction RIGHT
		list = board.getAdjList(5, 2);
		assertEquals(1, list.size());
		assert(list.contains(board.getCellAt(5, 3)));
		
		// cell with door direction LEFT
		list = board.getAdjList(4, 11);
		assertEquals(1, list.size());
		assert(list.contains(board.getCellAt(4, 10)));
		
		// cell with door direction UP
		list = board.getAdjList(19, 11);
		assertEquals(1, list.size());
		assert(list.contains(board.getCellAt(18, 11)));
		
		// cell with door direction DOWN
		list = board.getAdjList(8, 21);
		assertEquals(1, list.size());
		assert(list.contains(board.getCellAt(9, 21)));
		
		// cell with door direction RIGHT and walkway below
		list = board.getAdjList(7, 15);
		assertEquals(1, list.size());
		assert(list.contains(board.getCellAt(7, 16)));
	}
	
	// Test to confirm that adjacent doorways (with the correct direction) are added to the set
	@Test
	public void TestDoorwayAdjacencies() {
		Set<BoardCell> list = new HashSet<BoardCell>();
		
		// cell to the right of door direction RIGHT
		list = board.getAdjList(20, 7);
		assertEquals(3, list.size());
		assert(list.contains(board.getCellAt(20, 8)));
		assert(list.contains(board.getCellAt(19, 7)));
		assert(list.contains(board.getCellAt(21, 7)));
		
		// cell to the left of door direction LEFT
		list = board.getAdjList(4, 10);
		assertEquals(4, list.size());
		assert(list.contains(board.getCellAt(4, 11)));
		assert(list.contains(board.getCellAt(4, 9)));
		assert(list.contains(board.getCellAt(3, 10)));
		assert(list.contains(board.getCellAt(5, 10)));
		
		// cell above door direction UP
		list = board.getAdjList(18, 11);
		assertEquals(4, list.size());
		assert(list.contains(board.getCellAt(18, 10)));
		assert(list.contains(board.getCellAt(18, 12)));
		assert(list.contains(board.getCellAt(17, 11)));
		assert(list.contains(board.getCellAt(19, 11)));
		
		// cell below door direction DOWN
		list = board.getAdjList(9, 21);
		assertEquals(4, list.size());
		assert(list.contains(board.getCellAt(9, 20)));
		assert(list.contains(board.getCellAt(9, 22)));
		assert(list.contains(board.getCellAt(8, 21)));
		assert(list.contains(board.getCellAt(10, 21)));
	}
	
	// Test various walkway adjacencies
	@Test
	public void TestWalkwayAdjacencies() {
		Set<BoardCell> list = new HashSet<BoardCell>();
		
		// top edge of board next to one room piece
		list = board.getAdjList(0, 4);
		assertEquals(2, list.size());
		assert(list.contains(board.getCellAt(0, 3)));
		assert(list.contains(board.getCellAt(1, 4)));
		
		// left edge of board next to 3 walkways
		list = board.getAdjList(8, 0);
		assertEquals(3, list.size());
		assert(list.contains(board.getCellAt(7, 0)));
		assert(list.contains(board.getCellAt(9, 0)));
		assert(list.contains(board.getCellAt(8, 1)));
		
		// in the middle of 4 walkways
		list = board.getAdjList(15, 6);
		assertEquals(4, list.size());
		assert(list.contains(board.getCellAt(15, 5)));
		assert(list.contains(board.getCellAt(15, 7)));
		assert(list.contains(board.getCellAt(14, 6)));
		assert(list.contains(board.getCellAt(16, 6)));
		
		// right edge of board next to one room piece
		list = board.getAdjList(9, 23);
		assertEquals(2, list.size());
		assert(list.contains(board.getCellAt(10, 23)));
		assert(list.contains(board.getCellAt(9, 22)));
		
		// bottom edge of board next to one room piece
		list = board.getAdjList(23, 16);
		assertEquals(2, list.size());
		assert(list.contains(board.getCellAt(22, 16)));
		assert(list.contains(board.getCellAt(23, 17)));
		
		// next to door with the wrong direction to enter
		list = board.getAdjList(4, 2);
		assertEquals(2, list.size());
		assert(list.contains(board.getCellAt(3, 2)));
		assert(list.contains(board.getCellAt(4, 3)));
	}
	
	// Test targets for numSteps = 1
	@Test
	public void TestTargetsOneStep() {
		// Test 1 step starting at (15,8)
		board.calcTargets(15, 8, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assert(targets.contains(board.getCellAt(14, 8)));
		assert(targets.contains(board.getCellAt(16, 8)));
		assert(targets.contains(board.getCellAt(15, 7)));
		assert(targets.contains(board.getCellAt(15, 9)));
		
		// Test 1 step starting at (9,14)
		board.calcTargets(9, 14, 1);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assert(targets.contains(board.getCellAt(8, 14)));
		assert(targets.contains(board.getCellAt(9, 13)));
		assert(targets.contains(board.getCellAt(9, 15)));
	}
	
	// Test targets for numSteps = 3
	@Test
	public void TestTargetsThreeSteps() {
		// Test 3 steps starting at (6,18)
		board.calcTargets(6, 18, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(9, targets.size());
		assert(targets.contains(board.getCellAt(5, 18)));
		assert(targets.contains(board.getCellAt(3, 18)));
		assert(targets.contains(board.getCellAt(7, 18)));
		assert(targets.contains(board.getCellAt(9, 18)));
		assert(targets.contains(board.getCellAt(6, 19)));
		assert(targets.contains(board.getCellAt(4, 19)));
		assert(targets.contains(board.getCellAt(8, 19)));
		assert(targets.contains(board.getCellAt(8, 17)));
		assert(targets.contains(board.getCellAt(7, 16)));
		
		// Test 3 steps starting at (8,10)
		board.calcTargets(8, 10, 3);
		targets = board.getTargets();
		assertEquals(11, targets.size());
		assert(targets.contains(board.getCellAt(7, 10)));
		assert(targets.contains(board.getCellAt(5, 10)));
		assert(targets.contains(board.getCellAt(9, 10)));
		assert(targets.contains(board.getCellAt(8, 9)));
		assert(targets.contains(board.getCellAt(8, 7)));
		assert(targets.contains(board.getCellAt(8, 11)));
		assert(targets.contains(board.getCellAt(8, 13)));
		assert(targets.contains(board.getCellAt(7, 8)));
		assert(targets.contains(board.getCellAt(9, 8)));
		assert(targets.contains(board.getCellAt(9, 12)));
		assert(targets.contains(board.getCellAt(6, 9)));
	}
	
	// Test targets for numSteps = 4
	// Also, this tests getting into a room without using all of the steps
	@Test
	public void TestTargetsFourSteps() {
		board.calcTargets(17, 3, 4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(12, targets.size());
		assert(targets.contains(board.getCellAt(17, 5)));
		assert(targets.contains(board.getCellAt(17, 7)));
		assert(targets.contains(board.getCellAt(17, 1)));
		
		// (18, 2) is a doorway shortcut only needing 2 steps to enter
		assert(targets.contains(board.getCellAt(18, 2)));
		
		assert(targets.contains(board.getCellAt(16, 0)));
		assert(targets.contains(board.getCellAt(16, 2)));
		assert(targets.contains(board.getCellAt(16, 4)));
		assert(targets.contains(board.getCellAt(16, 6)));
		assert(targets.contains(board.getCellAt(15, 3)));
		assert(targets.contains(board.getCellAt(15, 5)));
		
		// (15, 2) is a doorway shortcut only needing 3 steps to enter
		assert(targets.contains(board.getCellAt(15, 2)));
		
		assert(targets.contains(board.getCellAt(14, 4)));
	}
	
	// Test targets when a room is exactly numSteps away from origin
	@Test
	public void TestTargetsIntoRoomExact() {
		board.calcTargets(2, 10, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(5, targets.size());
		assert(targets.contains(board.getCellAt(0, 10)));
		assert(targets.contains(board.getCellAt(4, 10)));
		assert(targets.contains(board.getCellAt(3, 9)));
		assert(targets.contains(board.getCellAt(1, 9)));
		
		// (2,8) is the doorway cell
		assert(targets.contains(board.getCellAt(2, 8)));
	}
	
	// Test targets when exiting a room
	@Test
	public void TestExitRoom() {
		// Testing the creation of targets starting at the door-cell (20,9) with 2 steps
		board.calcTargets(20, 9, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assert(targets.contains(board.getCellAt(20, 7)));
		assert(targets.contains(board.getCellAt(19, 8)));
		assert(targets.contains(board.getCellAt(21, 8)));
	}
}
