// Authors: Jonathon Kastner, Gavin Webster
// GameControlGUI Class

package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlGUI extends JPanel {
	
	private JTextField turnDisplay;
	private JTextField rollDisplay;
	private JTextField guessDisplay;
	private JTextField resultDisplay;

	// constructor for the GUi, add both top and bottom panels
	public GameControlGUI(Board board) {
		setLayout(new GridLayout(2,0));
		JPanel panel = createTopPanel(board);
		add(panel);
		panel = createBottomPanel();
		add(panel);
	}
	
	// create the top panel
	private JPanel createTopPanel(Board board) {
		JPanel panel = new JPanel();
		// use a grid layout with 3 columns
		panel.setLayout(new GridLayout(1,3));
		
		// create the 'Whose Turn?' panel and add to the top panel
		JPanel turnPanel = new JPanel();
		JLabel turnLabel = new JLabel("Whose turn?");
		turnPanel.add(turnLabel);
		turnDisplay = new JTextField(20);
		turnDisplay.setEditable(false);
		turnPanel.add(turnDisplay);
		panel.add(turnPanel);
		
		// add two buttons to the top panel
		JButton nextPlayer = new JButton("Next player");
		JButton makeAccusation = new JButton("Make an accusation");
		// make a class for the button listener
		class ButtonListener implements ActionListener {
			// has a GameControlGUI instance variable, used to update the displays
			private GameControlGUI gui;
			// constructor sets the gui variable to the parameter
			public ButtonListener(GameControlGUI g) {
				gui = g;
			}
			// one method for actionPerformed
			public void actionPerformed(ActionEvent e) {
				// if the button pushed was 'nextPlayer'
				if (e.getSource() == nextPlayer) {
					board.nextPlayerClicked(gui);
				}
				// if the button pushed was 'accusation'
				else if (e.getSource() == makeAccusation) {
					board.makeAccusationClicked();
				}
			}

		}
		// create the listener object
		ButtonListener listener = new ButtonListener(this);
		// add the listener to each button
		nextPlayer.addActionListener(listener);
		makeAccusation.addActionListener(listener);
		// add the buttons to the panel
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		return panel;
	}
	
	// create the bottom panel
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		// create the 'Die' panel
		JPanel diePanel = new JPanel();
		JLabel dieLabel = new JLabel("Roll");
		diePanel.add(dieLabel);
		diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		rollDisplay = new JTextField(3);
		rollDisplay.setEditable(false);
		diePanel.add(rollDisplay);
		
		// create the 'Guess' panel
		JPanel guessPanel = new JPanel();
		JLabel guessLabel = new JLabel("Guess");
		guessPanel.add(guessLabel);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guessDisplay = new JTextField(25);
		guessDisplay.setEditable(false);
		guessPanel.add(guessDisplay);
		
		// create the 'Guess Result' panel
		JPanel guessResultPanel = new JPanel();
		JLabel guessResultLabel = new JLabel("Response");
		guessResultPanel.add(guessResultLabel);
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		resultDisplay = new JTextField(15);
		resultDisplay.setEditable(false);
		guessResultPanel.add(resultDisplay);
		
		// add the three panels to the bottom panel
		panel.add(diePanel);
		panel.add(guessPanel);
		panel.add(guessResultPanel);
		return panel;
	}
	
	// main method
	public static void main(String[] args) {
		// make a frame, set the title, close operation, and size
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI");
		frame.setSize(700, 250);
		// make the GUI object, add it to the frame
		Board board = null;
		GameControlGUI gui = new GameControlGUI(board);
		frame.add(gui, BorderLayout.CENTER);
		// show the frame
		frame.setVisible(true);
	}
	
	// setters for the various text field displays (to update the display when running the game)
	public void setTurn(String str) {
		turnDisplay.setText(str);
	}
	public void setRoll(int n) {
		rollDisplay.setText(Integer.toString(n));
	}
	public void setGuess(String str) {
		guessDisplay.setText(str);
	}
	public void setResult(String str) {
		resultDisplay.setText(str);
	}
	
}
