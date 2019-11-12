// Authors: Jonathon Kastner, Gavin Webster
// CardsGUI Class

package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardsGUI extends JPanel{

	// constructor for the cards GUI, which displays the player's 3 cards
	public CardsGUI(Board board) {
		// find the user
		Player user = null;
		for (Player p : board.getPeople()) {
			if (p instanceof HumanPlayer) {
				user = p;
				break;
			}
		}
		// set border and layout
		setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		setLayout(new GridLayout(3,0));
		
		// create panel for the people card(s)
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		JTextArea peopleField = new JTextArea(7, 15);
		peopleField.setBackground(Color.WHITE);
		// set to not be editable, add to the panel
		peopleField.setEditable(false);
		peoplePanel.add(peopleField);
		
		// create panel for the room card(s)
		JPanel roomsPanel = new JPanel();
		roomsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		JTextArea roomsField = new JTextArea(7, 15);
		roomsField.setBackground(Color.WHITE);
		// set to not be editable, add to the panel
		roomsField.setEditable(false);
		roomsPanel.add(roomsField);
		
		// create panel for the weapon card(s)
		JPanel weaponsPanel = new JPanel();
		weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		JTextArea weaponsField = new JTextArea(7, 15);
		weaponsField.setBackground(Color.WHITE);
		// set to not be editable, add to the panel
		weaponsField.setEditable(false);
		weaponsPanel.add(weaponsField);
		
		// set the Textfield contents to the user's cards
		for (Card c : user.getHand()) {
			if (c.getType() == CardType.PERSON) {
				peopleField.append(c.getName() + "\n");
			}
			else if (c.getType() == CardType.ROOM) {
				roomsField.append(c.getName() + "\n");
			}
			else if (c.getType() == CardType.WEAPON) {
				weaponsField.append(c.getName() + "\n");
			}
		}
		
		// add all 3 panels to the CardsGUI panel
		add(peoplePanel);
		add(roomsPanel);
		add(weaponsPanel);
	}
}
