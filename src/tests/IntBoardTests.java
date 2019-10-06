// Authors: Jonathon Kastner and Gavin Webster
// IntBoardTests JUnit Test Class

package tests;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {

	// make an IntBoard
	IntBoard board;
	
	// before all tests, initialize the new board
	@Before
	public void beforeAll() {
		board = new IntBoard();
	}
	
	/*
	 * Tests for Adjacency Lists
	 */
	// Test for adjacency on the top left corner
	@Test
	public void testAdjacency0_0() {
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertEquals(2, testList.size());
	}
	// Test for adjacency on the bottom right corner
	@Test
	public void testAdjacency3_3() {
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(2, testList.size());
	}
	// Test for adjacency on the right side of the board
	@Test
	public void testAdjacency1_3() {
		BoardCell cell = board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0,3)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertEquals(3, testList.size());
	}
	// Test for adjacency on the left side of the board
	@Test
	public void testAdjacency3_0() {
		BoardCell cell = board.getCell(3,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2,0)));
		assertTrue(testList.contains(board.getCell(3,1)));
		assertEquals(2, testList.size());
	}
	// Test for adjacency on the second column of the grid
	@Test
	public void testAdjacency1_1() {
		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertEquals(4, testList.size());
	}
	// Test for adjacency on the second to last column of the grid
	@Test
	public void testAdjacency2_2() {
		BoardCell cell = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1,2)));
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * Tests for Target Lists
	 */
	// Test the targets for the point (0,0) with steps = 2
	@Test
	public void testTargets0_0_2() {
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		// there should be 3 targets, listed below
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(0,2)));
		assertTrue(targets.contains(board.getCell(2,0)));
		assertTrue(targets.contains(board.getCell(1,1)));
	}
	// Test the targets for the point (1,1) with steps = 1
	@Test
	public void testTargets1_1_1() {
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		// there should be 4 targets, listed below
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(0,1)));
		assertTrue(targets.contains(board.getCell(1,0)));
		assertTrue(targets.contains(board.getCell(1,2)));
		assertTrue(targets.contains(board.getCell(2,1)));
	}
	// Test the targets for the point (2,3) with steps = 2
	@Test
	public void testTargets2_3_2() {
		BoardCell cell = board.getCell(2, 3);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		// there should be 4 targets, listed below
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(0,3)));
		assertTrue(targets.contains(board.getCell(1,2)));
		assertTrue(targets.contains(board.getCell(3,2)));
		assertTrue(targets.contains(board.getCell(2,1)));
	}
	// Test the targets for the point (1,2) with steps = 5
	@Test
	public void testTargets1_2_5() {
		BoardCell cell = board.getCell(1, 2);
		board.calcTargets(cell, 5);
		Set targets = board.getTargets();
		// there should be 8 targets, listed below
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(0,0)));
		assertTrue(targets.contains(board.getCell(0,2)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(1,3)));
		assertTrue(targets.contains(board.getCell(2,0)));
		assertTrue(targets.contains(board.getCell(2,2)));
		assertTrue(targets.contains(board.getCell(3,1)));
		assertTrue(targets.contains(board.getCell(3,3)));
	}
	// Test the targets for the point (0,1) with steps = 3
	@Test
	public void testTargets0_1_3() {
		BoardCell cell = board.getCell(0, 1);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		// there should be 7 targets, listed below
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0,0)));
		assertTrue(targets.contains(board.getCell(0,2)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(1,3)));
		assertTrue(targets.contains(board.getCell(2,0)));
		assertTrue(targets.contains(board.getCell(2,2)));
		assertTrue(targets.contains(board.getCell(3,1)));
	}
	// Test the targets for the point (3,2) with steps = 4
	@Test
	public void testTargets3_2_4() {
		BoardCell cell = board.getCell(3, 2);
		board.calcTargets(cell, 4);
		Set targets = board.getTargets();
		// there should be 7 targets, listed below
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0,1)));
		assertTrue(targets.contains(board.getCell(0,3)));
		assertTrue(targets.contains(board.getCell(1,0)));
		assertTrue(targets.contains(board.getCell(1,2)));
		assertTrue(targets.contains(board.getCell(2,1)));
		assertTrue(targets.contains(board.getCell(2,3)));
		assertTrue(targets.contains(board.getCell(3,0)));
	}
}
