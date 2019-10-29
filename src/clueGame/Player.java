package clueGame;

import java.awt.Color;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<Card> myCards;
	private Set<Card> seenCards;
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	// Getter and Setters for testing Player creation
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
}
