// Authors: Jonathon Kastner and Gavin Webster
// Game Action JUnit Test Class

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import clueGame.*;

public class gameActionTests {

	// Initialize the board
	private static Board board;
	
	// Before method to set up the board and load the files
	@Before
	public void setUp() {
		board = board.getInstance();
		board.setConfigFiles("ClueMap.csv", "RoomLegend.txt");
		board.initialize();
		board.loadConfigFiles();
	}
	
	// Test for random target selection, if there are no rooms in the list
	@Test
	public void testRandomTargetSelectionNoRooms() {
		ComputerPlayer player = new ComputerPlayer("p", 14, 11, Color.BLUE);
		// calculate the targets
		board.calcTargets(14, 11, 2);
		boolean loc_14_9 = false;
		boolean loc_15_10 = false;
		boolean loc_16_11 = false;
		boolean loc_15_12 = false;
		boolean loc_14_13 = false;
		// run 100 trials, each location should be visited at least once
		for (int i = 0; i < 100; i++) {
			BoardCell target = player.pickLocation(board.getTargets());
			if (target == board.getCellAt(14, 9))
				 loc_14_9 = true;
			else if (target == board.getCellAt(15, 10))
				 loc_15_10 = true;
			else if (target == board.getCellAt(16, 11))
				 loc_16_11 = true;
			else if (target == board.getCellAt(15, 12))
				 loc_15_12 = true;
			else if (target == board.getCellAt(14, 13))
				 loc_14_13 = true;
			else
				 fail("Invalid target selected");
		}
		// make sure that each location was visited at least once
		assertTrue(loc_14_9);
		assertTrue(loc_15_10);
		assertTrue(loc_16_11);
		assertTrue(loc_15_12);
		assertTrue(loc_14_13);
	}
	
	// Test target selection when a room is in the target list
	@Test
	public void testTargetSelectionRoom() {
		ComputerPlayer player = new ComputerPlayer("p", 6, 6, Color.BLUE);
		// calculate the targets
		board.calcTargets(6, 6, 1);
		boolean loc_5_6 = false;
		boolean loc_7_6 = false;
		boolean loc_6_5 = false;
		boolean loc_6_7 = false;
		// run 10 trials, the room should be picked every single time
		for (int i = 0; i < 10; i++) {
			BoardCell target = player.pickLocation(board.getTargets());
			if (target == board.getCellAt(5, 6)) {
				loc_5_6 = true;
			}
			else if (target == board.getCellAt(7, 6)) {
				loc_7_6 = true;
			}
			else if (target == board.getCellAt(6, 5)) {
				loc_6_5 = true;
			}
			else if (target == board.getCellAt(6, 7)) {
				loc_6_7 = true;
			}
		}
		// make sure that the room was picked every single trial
		assertTrue(loc_5_6);
		assertFalse(loc_7_6);
		assertFalse(loc_6_5);
		assertFalse(loc_6_7);
	}
	
	// Test target selection when a room is present that was recently visited
	@Test
	public void testRandomTargetSelectionVisitedRoom() {
		ComputerPlayer player = new ComputerPlayer("p", 6, 6, Color.BLUE);
		// set the most recent room initial
		// if the room in the target list shares this initial, it will be part of the random target pool
		player.setRecentRoom('L');
		board.calcTargets(6, 6, 1);
		boolean loc_5_6 = false;
		boolean loc_7_6 = false;
		boolean loc_6_5 = false;
		boolean loc_6_7 = false;
		// run 100 trials, every target should be chosen, since it is random
		for (int i = 0; i < 100; i++) {
			BoardCell target = player.pickLocation(board.getTargets());
			if (target == board.getCellAt(5, 6)) {
				loc_5_6 = true;
			}
			else if (target == board.getCellAt(7, 6)) {
				loc_7_6 = true;
			}
			else if (target == board.getCellAt(6, 5)) {
				loc_6_5 = true;
			}
			else if (target == board.getCellAt(6, 7)) {
				loc_6_7 = true;
			}
			else {
				fail("Invalid Target Selected");
			}
		}
		// make sure that every target is chosen at least one time
		assertTrue(loc_5_6);
		assertTrue(loc_7_6);
		assertTrue(loc_6_5);
		assertTrue(loc_6_7);
	}
	
	// Test for the correct accusation
	@Test
	public void TestCorrectAccusation() {
		// create an accusation
		Solution accusation = new Solution();
		accusation.person = "Colonel Mustard";
		accusation.room = "Conservatory";
		accusation.weapon = "Gun";
		// set the game's solution to the same values
		board.setAnswer("Colonel Mustard", "Conservatory", "Gun");
		// make sure that the board views the accusation as correct
		assert(board.checkAccusation(accusation));
	}
	
	// Test for an accusation with the wrong person
	@Test
	public void TestAccusationWrongPerson() {
		// create an accusation
		Solution accusation = new Solution();
		accusation.person = "Mr. Green";
		accusation.room = "Conservatory";
		accusation.weapon = "Gun";
		// set the game's solution to have only a different person
		board.setAnswer("Colonel Mustard", "Conservatory", "Gun");
		// make sure that the accusation is viewed as incorrect
		assert(!board.checkAccusation(accusation));
	}
	
	// Test for an accusation with the wrong room
	@Test
	public void TestAccusationWrongRoom() {
		// create an accusation
		Solution accusation = new Solution();
		accusation.person = "Colonel Mustard";
		accusation.room = "Bedroom";
		accusation.weapon = "Gun";
		// set the game's solution to have only a different room
		board.setAnswer("Colonel Mustard", "Conservatory", "Gun");
		// make sure that the accusation is viewed as incorrect
		assert(!board.checkAccusation(accusation));
	}
	
	// Test for an accusation with the wrong weapon
	@Test
	public void TestAccusationWrongWeapon() {
		// create an accusation
		Solution accusation = new Solution();
		accusation.person = "Colonel Mustard";
		accusation.room = "Conservatory";
		accusation.weapon = "Knife";
		// set the game's solution to have only a different weapon
		board.setAnswer("Colonel Mustard", "Conservatory", "Gun");
		// make sure that the accusation is viewed as incorrect
		assert(!board.checkAccusation(accusation));
	}
	
	// Test the player disproving a suggestion when they have one matching card
	@Test
	public void TestDisproveSuggestionOneMatch() {
		// create a computer player with 3 cards in their hand
		ComputerPlayer player = new ComputerPlayer("p", 6, 6, Color.BLUE);
		Card c1 = new Card("Colonel Mustard", CardType.PERSON);
		Card c2 = new Card("Bedroom", CardType.ROOM);
		Card c3 = new Card("Poison", CardType.WEAPON);
		player.addCard(c1);
		player.addCard(c2);
		player.addCard(c3);
		// create a suggestion that has one card matching the player's cards
		Solution suggestion = new Solution();
		suggestion.person = "Colonel Mustard";
		suggestion.room = "Theatre";
		suggestion.weapon = "Gun";
		// the player should choose the one card that they have in common with the solution
		// if they choose that card, set the returnC1 boolean to true
		boolean returnC1 = false;
		if (player.disproveSuggestion(suggestion) == c1) {
			returnC1 = true;
		}
		// make sure that the boolean is set to true
		assertTrue(returnC1);
	}
	
	// Test the player disproving a suggestion when they have multiple matching cards
	@Test
	public void TestDisproveSuggestionMultipleMatches() {
		// create a computer player with 3 cards in their hand
		ComputerPlayer player = new ComputerPlayer("p", 6, 6, Color.BLUE);
		Card c1 = new Card("Colonel Mustard", CardType.PERSON);
		Card c2 = new Card("Bedroom", CardType.ROOM);
		Card c3 = new Card("Poison", CardType.WEAPON);
		player.addCard(c1);
		player.addCard(c2);
		player.addCard(c3);
		// create a suggestion that has multiple cards matching the player's cards
		Solution suggestion = new Solution();
		suggestion.person = "Colonel Mustard";
		suggestion.room = "Bedroom";
		suggestion.weapon = "Poison";
		// run 25 trials, the player should choose any of their cards at random
		// if they choose a card, set the corresponding boolean to true
		boolean returnC1 = false;
		boolean returnC2 = false;
		boolean returnC3 = false;
		for (int i = 0; i < 25; i++) {
			if (player.disproveSuggestion(suggestion) == c1) {
				returnC1 = true;
			}
			else if (player.disproveSuggestion(suggestion) == c2) {
				returnC2 = true;
			}
			else if (player.disproveSuggestion(suggestion) == c3) {
				returnC3 = true;
			}
		}
		// make sure that the booleans are set to true
		assertTrue(returnC1);
		assertTrue(returnC2);
		assertTrue(returnC3);
	}
	
	// Test the player disproving a suggestion when they have no matching cards
	@Test
	public void TestDisproveSuggestionNoMatches() {
		// create a computer player with 3 cards in their hand
		ComputerPlayer player = new ComputerPlayer("p", 6, 6, Color.BLUE);
		Card c1 = new Card("Colonel Mustard", CardType.PERSON);
		Card c2 = new Card("Bedroom", CardType.ROOM);
		Card c3 = new Card("Poison", CardType.WEAPON);
		player.addCard(c1);
		player.addCard(c2);
		player.addCard(c3);
		// create a suggestion that has no cards matching the player's cards
		Solution suggestion = new Solution();
		suggestion.person = "Miss Scarlet";
		suggestion.room = "Theatre";
		suggestion.weapon = "Knife";
		// the player should return no cards (null)
		assert(player.disproveSuggestion(suggestion) == null);
	}
	
	
}
