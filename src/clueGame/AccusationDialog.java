// Author: Jonathon Kastner, Gavin Webster
// AccusationDialog Class

package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AccusationDialog extends JDialog {

	// declare instance variables
	private JComboBox personBox;
	private JComboBox roomBox;
	private JComboBox weaponBox;
	
	// constructor creates the entire dialog
	public AccusationDialog(Board board, Player player) {
		// set the dialog options
		setTitle("Make an Accusation");
		setSize(400, 300);
		setModal(true);
		setLayout(new GridLayout(1, 2));
		
		// create the left panel, with 3 labels and a submit button
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(4, 1));
		JLabel roomLabel = new JLabel("Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		JButton submitButton = new JButton("Submit");
		// add the items to the left panel
		leftPanel.add(roomLabel);
		leftPanel.add(personLabel);
		leftPanel.add(weaponLabel);
		leftPanel.add(submitButton);

		// create the right panel, with 3 combo boxes and a cancel button
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4, 1));
		// populate the room box
		String[] rooms = new String[board.getRoomCards().size()];
		for (int i = 0; i < board.getRoomCards().size(); i++) {
			rooms[i] = board.getRoomCards().get(i).getName();
		}
		roomBox = new JComboBox(rooms);
		// populate the person box
		String[] people = new String[board.getPlayerCards().size()];
		for (int i = 0; i < board.getPlayerCards().size(); i++) {
			people[i] = board.getPlayerCards().get(i).getName();
		}
		personBox = new JComboBox(people);
		// populate the weapon box
		String[] weapons = new String[board.getWeaponCards().size()];
		for (int i = 0; i < board.getWeaponCards().size(); i++) {
			weapons[i] = board.getWeaponCards().get(i).getName();
		}
		weaponBox = new JComboBox(weapons);
		JButton cancelButton = new JButton("Cancel");
		// add the items to the right panel
		rightPanel.add(roomBox);
		rightPanel.add(personBox);
		rightPanel.add(weaponBox);
		rightPanel.add(cancelButton);

		// make a class for the button listener
		class ButtonListener implements ActionListener {
			private AccusationDialog dialog;
			// constructor sets the gui variable to the parameter
			public ButtonListener(AccusationDialog s) {
				dialog = s;
			}
			// one method for actionPerformed
			public void actionPerformed(ActionEvent e) {
				// if the button pushed was 'submit'
				if (e.getSource() == submitButton) {
					dialog.submitPressed(board, player);
				}
				// if the button pushed was 'cancel'
				else if (e.getSource() == cancelButton) {
					dialog.cancelPressed();
				}
			}
		}
		// create the listener object
		ButtonListener listener = new ButtonListener(this);
		// add the listener to each button
		submitButton.addActionListener(listener);
		cancelButton.addActionListener(listener);
		// add the left and right panels to the dialog
		add(leftPanel);
		add(rightPanel);
	}
	
	// method handling when the 'Submit' button is pressed
	public void submitPressed(Board board, Player player) {
		// hide the dialog
		this.setVisible(false);
		
		// create the accusation Solution from the combo box selections
		Solution guess = new Solution();
		guess.person = (String) personBox.getSelectedItem();
		guess.room = (String) roomBox.getSelectedItem();
		guess.weapon = (String) weaponBox.getSelectedItem();
		
		// create a result dialog, set the dialog's labels to the appropriate values
		ResultDialog result = new ResultDialog();
		result.setPlayerLabel(player.getName());
		String str = guess.person + ", " + guess.room + ", " + guess.weapon;
		result.setAccusationLabel(str);
		if (board.checkAccusation(guess) == true) {
			result.setResultLabel("Correct");
			// add a window listener to exit the program when the correct accusation window is closed
			result.addWindowListener(new WindowAdapter() {
				@Override public void windowClosed(WindowEvent e) {
					System.exit(0);
				}
			});
		}
		else {
			result.setResultLabel("Incorrect");
		}
		// show the result dialog
		result.setVisible(true);
		
	}
	
	// method handling when 'Cancel' is pressed
	public void cancelPressed() {
		// hide the dialog
		this.setVisible(false);
	}
}
