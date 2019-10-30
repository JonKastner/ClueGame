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
		board.setConfigFiles("ClueMap.csv", "RoomLegend.txt");
		board.initialize();
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
		Set<Card> deck = new HashSet<Card>();
		Set<Card> subDeck = new HashSet<Card>();
		
		// make sure there are 21 cards in the deck
		deck = board.getCards();
		assertEquals(21, deck.size());
		
		// 6 person cards
		subDeck = board.getPlayerCards();
		assertEquals(6, subDeck.size());
		
		// 6 weapon cards
		subDeck = board.getWeaponCards();
		assertEquals(6, subDeck.size());
		
		// 9 room cards
		subDeck = board.getRoomCards();
		assertEquals(9, subDeck.size());
		
		// pick a person, check to make sure that they are in the deck
		boolean contain = false;
		Card c = new Card("Colonel Mustard", CardType.PERSON);
		for (Card card : deck) {
			if (card.getName().equals(c.getName())){
				contain = true;
			}
		}
		assert(contain);
		contain = false;
		
		// pick a room, check to make sure that they are in the deck
		c = new Card("Conservatory", CardType.ROOM);
		for (Card card : deck) {
			if (card.getName().equals(c.getName())){
				contain = true;
			}
		}
		assert(contain);
		contain = false;
		
		// pick a weapon, check to make sure that they are in the deck
		c = new Card("Gun", CardType.WEAPON);
		for (Card card : deck) {
			if (card.getName().equals(c.getName())){
				contain = true;
			}
		}
		assert(contain);
	}
	
	@Test
	public void testDealCards() {
		//TODO
	}
}
