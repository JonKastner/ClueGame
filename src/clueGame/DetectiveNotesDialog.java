// Authors: Jonathon Kastner, Gavin Webster
// Detective Notes Dialog

package clueGame;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog {

	// constructor for the custom dialog
	public DetectiveNotesDialog(Board board) {
		
		// set the title, layout, and size
		setTitle("Detective Notes");
		setSize(500, 500);
		setLayout(new GridLayout(3, 2));
		
		// create the panel for the people
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		peoplePanel.setLayout(new GridLayout(0, 2));
		for (int i = 0; i < board.getPlayerCards().size(); i++) {
			JCheckBox box = new JCheckBox(board.getPlayerCards().get(i).getName());
			peoplePanel.add(box);
		}
		
		// create the panel for the person guess
		JPanel personGuessPanel = new JPanel();
		personGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		String[] people = new String[board.getPlayerCards().size() + 1];
		for (int i = 0; i < board.getPlayerCards().size(); i++) {
			people[i] = board.getPlayerCards().get(i).getName();
		}
		// add unsure to the combo box
		people[board.getPlayerCards().size()] = "Unsure";
		JComboBox pBox = new JComboBox(people);
		personGuessPanel.add(pBox);
		
		// create the panel for the rooms
		JPanel roomsPanel = new JPanel();
		roomsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		roomsPanel.setLayout(new GridLayout(0, 2));
		for (int i = 0; i < board.getRoomCards().size(); i++) {
			JCheckBox box = new JCheckBox(board.getRoomCards().get(i).getName());
			roomsPanel.add(box);
		}
		
		// create the panel for the room guess
		JPanel roomGuessPanel = new JPanel();
		roomGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		String[] rooms = new String[board.getRoomCards().size() + 1];
		for (int i = 0; i < board.getRoomCards().size(); i++) {
			rooms[i] = board.getRoomCards().get(i).getName();
		}
		// add unsure to the combo box
		rooms[board.getRoomCards().size()] = "Unsure";
		JComboBox rBox = new JComboBox(rooms);
		roomGuessPanel.add(rBox);
		
		// create the panel for the weapons
		JPanel weaponsPanel = new JPanel();
		weaponsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		weaponsPanel.setLayout(new GridLayout(0, 2));
		for (int i = 0; i < board.getWeaponCards().size(); i++) {
			JCheckBox box = new JCheckBox(board.getWeaponCards().get(i).getName());
			weaponsPanel.add(box);
		}
		
		// create the panel for the weapon guess
		JPanel weaponGuessPanel = new JPanel();
		weaponGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		String[] weapons = new String[board.getWeaponCards().size() + 1];
		for (int i = 0; i < board.getWeaponCards().size(); i++) {
			weapons[i] = board.getWeaponCards().get(i).getName();
		}
		// add unsure to the combo box
		weapons[board.getWeaponCards().size()] = "Unsure";
		JComboBox wBox = new JComboBox(weapons);
		weaponGuessPanel.add(wBox);

		// add all of the panels to the dialog
		add(peoplePanel);
		add(personGuessPanel);
		add(roomsPanel);
		add(roomGuessPanel);
		add(weaponsPanel);
		add(weaponGuessPanel);
	}
}
