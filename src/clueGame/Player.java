// Authors: Jonathon Kastner and Gavin Webster
// Player Parent Class

package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<Card> myCards;
	private Set<Card> seenCards;
	
	// constructor initializing all of the variable to appropriate values or parameters
	Player(String s, int r, int c, Color clr){
		playerName = s;
		row = r;
		column = c;
		color = clr;
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
	}
	
	// addCard function adds the parameterized Card to the Player's myCards Set
	public void addCard(Card c) {
		myCards.add(c);
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
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
	public Set<Card> getHand() {
		return myCards;
	}
}
