// Authors: Jonathon Kastner and Gavin Webster
// Game Action JUnit Test Class

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

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
			player.setRecentRoom(' ');
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
	
	// Test handling a suggestion that nobody can disprove
	@Test
	public void TestHandleSuggestionNobodyDisproves() {
		// make a suggestion that equals the game's solution
		Solution suggestion = new Solution();
		suggestion.person = board.getAnswer().person;
		suggestion.room = board.getAnswer().room;
		suggestion.weapon = board.getAnswer().weapon;
		// make sure that the board returns null when handling the suggestion, picked a random player to provide the suggestion (doesn't change result)
		assert(board.handleSuggestion(board.getPeople().get(0), suggestion) == null);
	}
	
	// Test handling a suggestion that only the accuser can disprove
	@Test
	public void TestHandleSuggestionOnlyAccuserDisproves() {
		// pick a computer player for the test, they will be the accuser
		Player player = null;
		for (Player p : board.getPeople()) {
			if (p instanceof ComputerPlayer) {
				player = p;
				break;
			}
		}
		// make a suggestion that only they can disprove
		// do this by picking a card from their hand, using that in the suggestion, and setting the other two values of the suggestion to the game's solution values
		Solution suggestion = new Solution();
		Card c = player.getHand().get(0);
		if (c.getType() == CardType.PERSON) {
			suggestion.person = c.getName();
			suggestion.room = board.getAnswer().room;
			suggestion.weapon = board.getAnswer().weapon;
		}
		else if (c.getType() == CardType.ROOM) {
			suggestion.room = c.getName();
			suggestion.person = board.getAnswer().person;
			suggestion.weapon = board.getAnswer().weapon;
		}
		else if (c.getType() == CardType.WEAPON) {
			suggestion.weapon = c.getName();
			suggestion.person = board.getAnswer().person;
			suggestion.room = board.getAnswer().room;
		}
		// make sure that the board handles the suggestion by returning null
		assert(board.handleSuggestion(player, suggestion) == null);
	}
	
	// Test handling a suggestion that only the user can disprove
	@Test
	public void TestHandleSuggestionOnlyUserDisproves() {
		// pick the human player, they will be only one who can disprove
		Player player = null;
		for (Player p : board.getPeople()) {
			if (p instanceof HumanPlayer) {
				player = p;
				break;
			}
		}
		// make another player to act as the accuser, only for the test
		Player accuser = new ComputerPlayer("CP1", 6, 6, Color.BLUE);
		// make a suggestion that only they can disprove
		// do this by picking a card from their hand, using that in the suggestion, and setting the other two values of the suggestion to the game's solution values
		Solution suggestion = new Solution();
		Card c = player.getHand().get(0);
		if (c.getType() == CardType.PERSON) {
			suggestion.person = c.getName();
			suggestion.room = board.getAnswer().room;
			suggestion.weapon = board.getAnswer().weapon;
		}
		else if (c.getType() == CardType.ROOM) {
			suggestion.room = c.getName();
			suggestion.person = board.getAnswer().person;
			suggestion.weapon = board.getAnswer().weapon;
		}
		else if (c.getType() == CardType.WEAPON) {
			suggestion.weapon = c.getName();
			suggestion.person = board.getAnswer().person;
			suggestion.room = board.getAnswer().room;
		}
		// make sure that the board handles the suggestion by returning the matching card from the user's hand
		assert(board.handleSuggestion(accuser, suggestion) == c);
	}
	
	// Test handling the user's suggestion that only the user can disprove
	@Test
	public void TestHandleUserSuggestionOnlyUserDisproves() {
		// pick the human player for the test, they will be the accuser
		Player player = null;
		for (Player p : board.getPeople()) {
			if (p instanceof HumanPlayer) {
				player = p;
				break;
			}
		}
		// make a suggestion that only they can disprove
		// do this by picking a card from their hand, using that in the suggestion, and setting the other two values of the suggestion to the game's solution values
		Solution suggestion = new Solution();
		Card c = player.getHand().get(0);
		if (c.getType() == CardType.PERSON) {
			suggestion.person = c.getName();
			suggestion.room = board.getAnswer().room;
			suggestion.weapon = board.getAnswer().weapon;
		}
		else if (c.getType() == CardType.ROOM) {
			suggestion.room = c.getName();
			suggestion.person = board.getAnswer().person;
			suggestion.weapon = board.getAnswer().weapon;
		}
		else if (c.getType() == CardType.WEAPON) {
			suggestion.weapon = c.getName();
			suggestion.person = board.getAnswer().person;
			suggestion.room = board.getAnswer().room;
		}
		// make sure that the board handles the suggestion by returning null
		assert(board.handleSuggestion(player, suggestion) == null);
	}
	
	// Test handling a suggestion that multiple computer players can disprove
	@Test
	public void TestHandleSuggestionMultipleComputerDisproves() {
		Player player1 = null;
		Player player2 = null;
		// pick the two computer players that can disprove
		for (Player p : board.getPeople()) {
			if (p instanceof ComputerPlayer) {
				player1 = p;
				break;
			}
		}
		for (Player p : board.getPeople()) {
			if (p instanceof ComputerPlayer && p != player1) {
				player2 = p;
				break;
			}
		}
		// make the accuser the player in the list after player2
		Player accuser = board.getPeople().get(board.getPeople().indexOf(player2) + 1);
		// make a test suggestion that the two players can disprove
		// do this by picking a card from each of the others' hands, using that in the suggestion, and setting the other value of the suggestion to the game's solution value
		Solution suggestion = new Solution();
		Card c1 = null;
		Card c2 = null;
		// search through each player's cards together to find two cards that don't share the same type
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (player1.getHand().get(i).getType() != player2.getHand().get(j).getType()) {
					c1 = player1.getHand().get(i);
					c2 = player2.getHand().get(j);
					i = 3;
					j = 3;
				}
			}
		}
		// set the suggestion values to the two cards' values
		if (c1.getType() == CardType.PERSON) {
			suggestion.person = c1.getName();
		}
		else if (c1.getType() == CardType.ROOM) {
			suggestion.room = c1.getName();
		}
		else if (c1.getType() == CardType.WEAPON) {
			suggestion.weapon = c1.getName();
		}
		if (c2.getType() == CardType.PERSON) {
			suggestion.person = c2.getName();
		}
		else if (c2.getType() == CardType.ROOM) {
			suggestion.room = c2.getName();
		}
		else if (c2.getType() == CardType.WEAPON) {
			suggestion.weapon = c2.getName();
		}
		// make sure that the board handles the suggestion by returning the card from the next player in the list
		// the card should be returned by player 1, since player 1 comes before player 2 in the list
		assert(board.handleSuggestion(accuser, suggestion) == c1);
	}
	
	// Test handling a suggestion that multiple people can disprove, one being the user
	@Test
	public void TestHandleSuggestionMultipleUserDisproves() {
		Player player1 = null;
		Player user = null;
		// pick the user, they will disprove
		for (Player p : board.getPeople()) {
			if (p instanceof HumanPlayer) {
				user = p;
				break;
			}
		}
		// the computer player that can also disprove is the player after the user in the list
		if (board.getPeople().indexOf(user) == board.getPeople().size() - 1) {
			player1 = board.getPeople().get(0);
		}
		else {
			player1 = board.getPeople().get(board.getPeople().indexOf(user) + 1);
		}
		// make the accuser the player in the list after player2
		Player accuser = board.getPeople().get(board.getPeople().indexOf(player1) + 1);
		// make a test suggestion that the two players can disprove
		// do this by picking a card from each of the others' hands, using that in the suggestion, and setting the other value of the suggestion to the game's solution value
		Solution suggestion = new Solution();
		Card c1 = null;
		Card c2 = null;
		// search through each player's cards together to find two cards that don't share the same type
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (player1.getHand().get(i).getType() != user.getHand().get(j).getType()) {
					c1 = player1.getHand().get(i);
					c2 = user.getHand().get(j);
					i = 3;
					j = 3;
				}
			}
		}
		// set the suggestion values to the two cards' values
		if (c1.getType() == CardType.PERSON) {
			suggestion.person = c1.getName();
		}
		else if (c1.getType() == CardType.ROOM) {
			suggestion.room = c1.getName();
		}
		else if (c1.getType() == CardType.WEAPON) {
			suggestion.weapon = c1.getName();
		}
		if (c2.getType() == CardType.PERSON) {
			suggestion.person = c2.getName();
		}
		else if (c2.getType() == CardType.ROOM) {
			suggestion.room = c2.getName();
		}
		else if (c2.getType() == CardType.WEAPON) {
			suggestion.weapon = c2.getName();
		}
		// make sure that the board handles the suggestion by returning the card from the next player in the list
		// the card should be returned by the user, since the user comes before player1 in the list
		assert(board.handleSuggestion(accuser, suggestion) == c2);
	}
	
	// Test creating a suggestion making sure that the room matches the current location
	@Test
	public void TestCreateSuggestionRoomMatches() {
		// create a computer player for the test at point (0,0)
		// (0,0) is inside of the Office 'O'
		ComputerPlayer player = new ComputerPlayer("CP1", 0, 0, Color.BLUE);
		player.setRoomInitial('O');
		String roomName = player.createSuggestion(board.getPlayerCards(), board.getWeaponCards()).room;
		assert(roomName == "Office");
	}
	
	// Tests creating a suggestion where only one weapon is not seen, chooses that weapon
	@Test
	public void TestCreateSuggestionOneWeaponSelection() {
		// create a computer player for the test at point (0,0)
		// (0,0) is inside of the Office 'O'
		ComputerPlayer player = new ComputerPlayer("CP1", 0, 0, Color.BLUE);
		player.setRoomInitial('O');
		// add all weapons except one to the player's seenCards list
		ArrayList<Card> Weapons = board.getWeaponCards();
		Card card = Weapons.get(0);
		for (Card c : Weapons) {
			if (c != card) {
				player.addSeen(c);
			}
		}
		// make sure that the weapon selected is the only one not seen
		String weaponName = player.createSuggestion(board.getPlayerCards(), board.getWeaponCards()).weapon;
		assert(weaponName.equals(card.getName()));
	}
	
	// Tests creating a suggestion where only one person is not seen, chooses that person
	@Test
	public void TestCreateSuggestionOnePersonSelection() {
		// create a computer player for the test at point (0,0)
		// (0,0) is inside of the Office 'O'
		ComputerPlayer player = new ComputerPlayer("CP1", 0, 0, Color.BLUE);
		player.setRoomInitial('O');
		// add all weapons except one to the player's seenCards list
		ArrayList<Card> People = board.getPlayerCards();
		Card card = People.get(0);
		for (Card c : People) {
			if (c != card) {
				player.addSeen(c);
			}
		}
		// make sure that the weapon selected is the only one not seen
		String personName = player.createSuggestion(board.getPlayerCards(), board.getWeaponCards()).person;
		assert(personName.equals(card.getName()));
	}
	
	// Tests creating a suggestion where weapon is randomly selected
	@Test
	public void TestCreateSuggestionRandomWeaponSelection() {
		// create a computer player for the test at point (0,0)
		// (0,0) is inside of the Office 'O'
		ComputerPlayer player = new ComputerPlayer("CP1", 0, 0, Color.BLUE);
		player.setRoomInitial('O');
		// add all weapons except three to the player's seenCards list
		ArrayList<Card> Weapons = board.getWeaponCards();
		Card card1 = Weapons.get(0);
		Card card2 = Weapons.get(1);
		Card card3 = Weapons.get(2);
		for (Card c : Weapons) {
			if (c != card1 && c != card2 && c != card3) {
				player.addSeen(c);
			}
		}
		boolean c1 = false;
		boolean c2 = false;
		boolean c3 = false;
		// run 25 trials, make sure that each boolean is set to true by the end of the trials to prove random selection
		for (int i = 0; i < 25; i++) {
			String weaponName = player.createSuggestion(board.getPlayerCards(), board.getWeaponCards()).weapon;
			if (weaponName.equals(card1.getName())) {
				c1 = true;
			}
			else if (weaponName.equals(card2.getName())) {
				c2 = true;
			}
			else if (weaponName.equals(card3.getName())) {
				c3 = true;
			}
		}
		Set<Card> cards = board.getCards();
		assert(c1);
		assert(c2);
		assert(c3);
	}
	
	// Tests creating a suggestion where person is randomly selected
	@Test
	public void TestCreateSuggestionRandomPersonSelection() {
		// create a computer player for the test at point (0,0)
		// (0,0) is inside of the Office 'O'
		ComputerPlayer player = new ComputerPlayer("CP1", 0, 0, Color.BLUE);
		player.setRoomInitial('O');
		// add all weapons except three to the player's seenCards list
		player.addSeen(new Card("Colonel Mustard", CardType.PERSON));
		player.addSeen(new Card("Miss Scarlet", CardType.PERSON));
		player.addSeen(new Card("Mr. Green", CardType.PERSON));
		boolean mrsWhite = false;
		boolean profPlum = false;
		boolean mrsPeacock = false;
		// run 25 trials, make sure that each boolean is set to true by the end of the trials to prove random selection
		for (int i = 0; i < 25; i++) {
			String personName = player.createSuggestion(board.getPlayerCards(), board.getWeaponCards()).person;
			if (personName.equals("Mrs. White")) {
				mrsWhite = true;
			}
			else if (personName.equals("Professor Plum")) {
				profPlum = true;
			}
			else if (personName.equals("Mrs. Peacock")) {
				mrsPeacock = true;
			}
		}
		assert(mrsWhite);
		assert(profPlum);
		assert(mrsPeacock);
	}
}
