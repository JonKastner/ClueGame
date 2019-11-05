// Authors: Jonathon Kastner and Gavin Webster
// Player Parent Class

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private Set<Card> seenCards;
	
	// constructor initializing all of the variable to appropriate values or parameters
	Player(String s, int r, int c, Color clr){
		playerName = s;
		row = r;
		column = c;
		color = clr;
		myCards = new ArrayList<Card>();
		seenCards = new HashSet<Card>();
	}
	
	// addCard function adds the parameterized Card to the Player's myCards Set
	public void addCard(Card c) {
		myCards.add(c);
	}
	
	// disproveSuggestion function handles how all players will disprove a suggestion
	public Card disproveSuggestion(Solution suggestion) {
		// find any matching cards to the suggestion in the player's hand
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		for (Card c : myCards) {
			// if the card shares the same name as any part of the solution, add to the matching cards list
			if (c.getName() == suggestion.person || c.getName() == suggestion.room || c.getName() == suggestion.weapon) {
				matchingCards.add(c);
			}
		}
		// if there is one card in matchingCards, return it
		if (matchingCards.size() == 1) {
			return matchingCards.get(0);
		}
		// if there are no matching cards, return null
				else if (matchingCards.size() == 0) {
					return null;
				}
		// if there are multiple cards in matchingCards, return a random one
		else {
			Random rand = new Random();
			int randomNum = Math.abs(rand.nextInt() % matchingCards.size());
			return matchingCards.get(randomNum);
		}
	}
	
	// Getter and Setters for testing purposes only
	public String getName() {
		return playerName;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return column;
	}
	public Color getColor() {
		return color;
	}
	public ArrayList<Card> getHand() {
		return myCards;
	}
}
