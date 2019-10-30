package clueGame;

public class Card {

	private String cardName;
	private CardType cardType;
	
	public Card(String s, CardType c){
		cardName = s;
		cardType = c;
	}
	
	public boolean equals() {
		return false;
	}
	
	// getter for testing purposes only
	public CardType getType() {
		return cardType;
	}
	public String getName() {
		return cardName;
	}
}
