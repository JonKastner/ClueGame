// Authors: Jonathon Kastner, Gavin Webster
// ResultDialog Class

package clueGame;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ResultDialog extends JDialog {
	
	// declare instance variables
	private JLabel playerLabel;
	private JLabel accusationLabel;
	private JLabel resultLabel;
	
	// constructor creates the entire dialog
	public ResultDialog() {
		// set the dialog options
		setTitle("Accusation Result");
		setSize(500, 250);
		setModal(true);
		setLayout(new GridLayout(3, 1));
		// set the close operation for the window listener, used if an accusation is correct
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// initialize the 3 labels
		playerLabel = new JLabel();
		accusationLabel = new JLabel();
		resultLabel = new JLabel();
		
		// add the labels to the dialog
		add(playerLabel);
		add(accusationLabel);
		add(resultLabel);
	}
	
	// setters used to set the text of each label from the accusation methods
	public void setPlayerLabel(String s) {
		playerLabel.setText("Accuser: " + s);
	}
	public void setAccusationLabel(String s) {
		accusationLabel.setText("Accusation: " + s);
	}
	public void setResultLabel(String s) {
		resultLabel.setText("Result: " + s);
	}
}
