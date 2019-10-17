// Jonathon Kastner and Gavin Webster
// JUnit Tests for the board

package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class BoardTests {

	// Initialize the board
	private static Board board;
	
	// Before method to set up the board and load in the configuration files
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueMap.csv", "RoomLegend.txt");		 
		board.initialize();
	}
	
	// Test for the room legend
	// Ensures that the legend is the correct size (correct number of rows from the file)
	// Checks some of the rooms to make sure that their name matches their initial
	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(11, legend.size());
		assertEquals("Office", legend.get('O'));
		assertEquals("Bedroom", legend.get('E'));
		assertEquals("Hall", legend.get('H'));
		assertEquals("Theatre", legend.get('T'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	// Test the board dimensions
	// reads in the number of rows and columns, and makes sure that they are equal to the correct board size (24x24)
	@Test
	public void testBoardDimensions() {
		assertEquals(24, board.getNumRows());
		assertEquals(24, board.getNumColumns());		
	}
	
	// Test the door directions
	@Test
	public void FourDoorDirections() {
		// make sure that the cell at (2,5) is a doorway accessible from the right
		BoardCell room = board.getCellAt(5, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		// make sure that the cell at (0,6) is a doorway accessible from below
		room = board.getCellAt(6, 0);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		// make sure that the cell at (4,11) is a doorway accessible from the left
		room = board.getCellAt(4, 11);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		// make sure that the cell at (19,11) is a doorway accessible from above
		room = board.getCellAt(19, 11);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test a cell that is not a door
		room = board.getCellAt(14, 4);
		assertFalse(room.isDoorway());	
		// Ensure that walkways are not read in as doors
		BoardCell cell = board.getCellAt(7, 12);
		assertFalse(cell.isDoorway());		
	}
	
	// Testing the total number of doorways on the board
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		// Test every cell, if it is a doorway, increment the number of doors
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		// Check the total number of doors on the board
		Assert.assertEquals(21, numDoors);
	}
	
	// Test some of the rooms' initials
	@Test
	public void testRoomInitials() {
		// Test first cell in some rooms
		assertEquals('O', board.getCellAt(0, 0).getInitial());
		assertEquals('W', board.getCellAt(0, 4).getInitial());
		assertEquals('B', board.getCellAt(10, 0).getInitial());
		// Test last cell in some rooms
		assertEquals('H', board.getCellAt(23, 15).getInitial());
		assertEquals('T', board.getCellAt(23, 23).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(8, 8).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(12, 13).getInitial());
	}
	
	
}
