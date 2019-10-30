package tests;

import clueGame.*;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class gameSetupTests {

	private static Board board;
	@Before
	public void setUp() {
		board = board.getInstance();
		board.loadConfigFiles();
	}
	
	@Test
	public void testLoadPeople() {
		ArrayList<Player> list = new ArrayList<Player>();
		list = board.getPeople();
		
		// Test to make sure there are 6 players
		assertEquals(list.size(), 6);
		
		// Test the first player's values (computer player)
		// Also tests to make sure that they are a ComputerPlayer
		Player p = list.get(0);
		assertEquals("Colonel Mustard", p.getName());
		assertEquals(0, p.getRow());
		assertEquals(4, p.getCol());
		assertEquals(Color.YELLOW, p.getColor());
		assert(p instanceof HumanPlayer);
		
		// Test the human player's values
		// Also tests to make sure that they are a HumanPlayer
		p = list.get(3);
		assertEquals("Mrs. Peacock", p.getName());
		assertEquals(17, p.getRow());
		assertEquals(23, p.getCol());
		assertEquals(Color.BLUE, p.getColor());
		assert(p instanceof ComputerPlayer);
		
		// Test the last player's values (computer player)
		// Also tests to make sure that they are a ComputerPlayer
		p = list.get(5);
		assertEquals("Professor Plum", p.getName());
		assertEquals(0, p.getRow());
		assertEquals(19, p.getCol());
		assertEquals(Color.MAGENTA, p.getColor());
		assert(p instanceof ComputerPlayer);
	}
	
	@Test
	public void testLoadCreateCards() {
		//TODO
	}
	
	@Test
	public void testDealCards() {
		//TODO
	}
}
