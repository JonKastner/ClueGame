// Authors: Jonathon Kastner and Gavin Webster
// Player Parent Class

package clueGame;

import java.awt.Color;
import java.awt.Graphics2D;
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
	private ArrayList<Card> seenCards;
	private char roomInitial;
	private static int CELL_DIMENSION = 20;
	private static int CIRCLE_WIDTH = 18;
	private static int CIRCLE_HEIGHT = 18;
	
	// constructor initializing all of the variable to appropriate values or parameters
	Player(String s, int r, int c, Color clr){
		playerName = s;
		row = r;
		column = c;
		color = clr;
		myCards = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
	}
	
	// addCard function adds the parameterized Card to the Player's myCards Set
	public void addCard(Card c) {
		myCards.add(c);
	}
	
	// addSeen function adds the card to the seenCards list
	public void addSeen(Card c) {
		seenCards.add(c);
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
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.drawOval(column * CELL_DIMENSION + 1, row * CELL_DIMENSION + 1, CIRCLE_WIDTH, CIRCLE_HEIGHT);
		g.fillOval(column * CELL_DIMENSION + 1, row * CELL_DIMENSION + 1, CIRCLE_WIDTH, CIRCLE_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawOval(column * CELL_DIMENSION + 1, row * CELL_DIMENSION + 1, CIRCLE_WIDTH, CIRCLE_HEIGHT);
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
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
	public void setRoomInitial(char c) {
		roomInitial = c;
	}
	public char getRoomInitial() {
		return roomInitial;
	}
}
