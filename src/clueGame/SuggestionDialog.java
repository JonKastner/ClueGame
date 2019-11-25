// Authors: Jonathon Kastner, Gavin Webster
// SuggestionDialog Class

package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuggestionDialog extends JDialog {
	
	// declare instance variables
	private JComboBox personBox;
	private JComboBox weaponBox;
	private JTextField roomField;
	private Solution guess = null;

	// constructor creates the entire dialog
	public SuggestionDialog(BoardCell cell, Board board, Player player, GameControlGUI gui) {
		// set the dialog options
		setTitle("Make a Guess");
		setModal(true);
		setSize(400, 300);
		setLayout(new GridLayout(1, 2));
		
		// create the left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(4, 1));
		// the left panel has 3 labels and the 'Submit' button
		JLabel roomLabel = new JLabel("Your Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		JButton submitButton = new JButton("Submit");
		// add all of the items to the left panel
		leftPanel.add(roomLabel);
		leftPanel.add(personLabel);
		leftPanel.add(weaponLabel);
		leftPanel.add(submitButton);
		
		// create the right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4, 1));
		roomField = new JTextField();
		// set the room field
		roomField.setText(board.getLegend().get(cell.getInitial()));
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
		// create the 'Cancel' button
		JButton cancelButton = new JButton("Cancel");
		// add all of the items to the right panel
		rightPanel.add(roomField);
		rightPanel.add(personBox);
		rightPanel.add(weaponBox);
		rightPanel.add(cancelButton);
		
		// make a class for the button listener
		class ButtonListener implements ActionListener {
			private SuggestionDialog dialog;
			// constructor sets the gui variable to the parameter
			public ButtonListener(SuggestionDialog s) {
				dialog = s;
			}
			// one method for actionPerformed
			public void actionPerformed(ActionEvent e) {
				// if the button pushed was 'submit'
				if (e.getSource() == submitButton) {
					dialog.submitPressed(board, player, gui);
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
	
	// method handling when 'Submit' is pressed
	public void submitPressed(Board board, Player player, GameControlGUI gui) {
		// create the guess from the combo box selections and the room label
		guess = new Solution();
		guess.person = (String) personBox.getSelectedItem();
		guess.room = roomField.getText();
		guess.weapon = (String) weaponBox.getSelectedItem();
		
		// set the GUI guess field
		String s = guess.person + ", " + guess.room + ", " + guess.weapon;
		gui.setGuess(s);
		// set the GUI result field based on the result from the board handling the suggestion
		if (board.handleSuggestion(player, guess) == null) {
			gui.setResult("No new clue");
		} else {
			gui.setResult(board.handleSuggestion(player, guess).getName());
		}
		// Find the player that was suggested, move them to the accuser's location
		for (Player p : board.getPeople()) {
			if (p.getName().equals(guess.person)) {
				p.setLocation(player.getRow(), player.getCol());
				break;
			}
		}
		// hide the dialog
		this.setVisible(false);
	}
	
	// method handling when 'Cancel' is pressed
	public void cancelPressed() {
		// simply hide the window
		this.setVisible(false);
	}
}
