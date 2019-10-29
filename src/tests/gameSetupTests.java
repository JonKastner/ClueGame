package tests;

import clueGame.*;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
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
		Set<Player> list = new HashSet<Player>();
		list = board.getPeople();
		
		// Test to make sure there are 6 players
		assertEquals(list.size(), 6);
		ArrayList<Player> list2 = new ArrayList<Player>(list);
		
		// Test the first player's values (computer player)
		Player p = list2.get(0);
		assertEquals(p.getName(), "Colonel Mustard");
		assertEquals(p.getRow(), 0);
		assertEquals(p.getCol(), 4);
		assertEquals(p.getColor(), Color.YELLOW);
		
		// Test the human player's values
		p = list2.get(3);
		assertEquals(p.getName(), "Mrs. Peacock");
		assertEquals(p.getRow(), 17);
		assertEquals(p.getCol(), 23);
		assertEquals(p.getColor(), Color.BLUE);
		
		// Test the last player's values (computer player)
		p = list2.get(5);
		assertEquals(p.getName(), "Professor Plum");
		assertEquals(p.getRow(), 0);
		assertEquals(p.getCol(), 19);
		assertEquals(p.getColor(), Color.MAGENTA);
	}
	
	@Test
	public void testLoadCreateCards() {
		
	}
	
	@Test
	public void testDealCards() {
		
	}
}
