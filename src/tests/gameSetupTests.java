// Authors: Jonathon Kastner and Gavin Webster
// GameSetup JUnit Test Class

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

	// initialize the board
	private static Board board;
	// Before method to set up the board and call appropriate functions
	@Before
	public void setUp() {
		board = board.getInstance();
		board.setConfigFiles("ClueMap.csv", "RoomLegend.txt");
		board.initialize();
		board.loadConfigFiles();
	}
	
	// Test loading the players
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
	
	// Test Creating/Loading all of the cards
	@Test
	public void testLoadCreateCards() {
		Set<Card> deck = new HashSet<Card>();
		ArrayList<Card> subDeck = new ArrayList<Card>();
		
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
		
		// pick a room, check to make sure that it is in the deck
		c = new Card("Conservatory", CardType.ROOM);
		for (Card card : deck) {
			if (card.getName().equals(c.getName())){
				contain = true;
			}
		}
		assert(contain);
		contain = false;
		
		// pick a weapon, check to make sure that it is in the deck
		c = new Card("Gun", CardType.WEAPON);
		for (Card card : deck) {
			if (card.getName().equals(c.getName())){
				contain = true;
			}
		}
		assert(contain);
	}
	
	// Test dealing the cards, and creating the solution
	@Test
	public void testDealCards() {
		ArrayList<Player> playerList = new ArrayList<Player>();
		Set<Card> deck = new HashSet<Card>();
		playerList = board.getPeople();
		deck = board.getDealtCards();
		
		// make sure all cards are dealt
		assertEquals(21, deck.size());
		
		// all player should have the same number of cards
		int cardCount = playerList.get(0).getHand().size();
		boolean sameNumber = true;
		for (Player p : playerList) {
			if (!(cardCount - p.getHand().size() <= 1 && cardCount - p.getHand().size() >= -1)) {
				sameNumber = false;
			}
		}
		assert(sameNumber);
		
		// no duplicate cards dealt to different players
		deck = board.getCards();
		for (Card c : deck) {
			int count = 0;
			for (Player p : playerList) {
				// If the card is in a player's hand, increment count
				if (p.getHand().contains(c)) {
					count++;
				}
			}
			// If the card is part of the solution, increment count
			if ((c.getName().equals(board.getAnswer().room)) || (c.getName().equals(board.getAnswer().person)) || (c.getName().equals(board.getAnswer().weapon))) {
				count++;
			}
			assertEquals(1, count);
		}
		
	}
}
