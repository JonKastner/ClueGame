// Authors: Jonathon Kastner, Gavin Webster
// ClueGame Class

package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame{

	// initialize the Board and Detective Notes instance variables
	private static Board board;
	private DetectiveNotesDialog notes;
	
	// constructor creates the game GUI
	public ClueGame() {
		// set the frame options
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ClueGame");
		setSize(750, 700);
		//setLayout(new GridLayout(2,0));
		
		// grab the board, and initialize it
		board = board.getInstance();
		board.setConfigFiles("ClueMap.csv", "RoomLegend.txt");
		board.initialize();
		board.loadConfigFiles();
		// initialize the notes dialog
		notes = new DetectiveNotesDialog(board);
		// add the board to the frame
		add(board, BorderLayout.CENTER);
		// create the GameControlGUI and add it to the frame
		GameControlGUI gui = new GameControlGUI();
		add(gui, BorderLayout.SOUTH);
		// create the my_cards panel and add it to the frame
		CardsGUI myCardsPanel = new CardsGUI();
		add(myCardsPanel, BorderLayout.EAST);
		// add the menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createMenu());
	}
	
	// creates the menu bar
	private JMenu createMenu() {
		JMenu menu = new JMenu("Menu");
		// add two items, the notes item and the exit item
		menu.add(createMenuNotesItem());
		menu.add(createMenuExitItem());
		return menu;
	}
	
	// creates the notes menu item
	private JMenuItem createMenuNotesItem() {
		// it is called "Detective Notes"
		JMenuItem item = new JMenuItem("Detective Notes");
		// Action listener class that sets the notes dialog to visible when the menu item is clicked
		class NotesListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				notes.setVisible(true);
			}
		}
		// add the action listener
		item.addActionListener(new NotesListener());
		return item;
	}
	
	// creates the menu exit item
	private JMenuItem createMenuExitItem() {
		// it is called "Exit"
		JMenuItem item = new JMenuItem("Exit");
		// Action listener class closes the program when the menu item is clicked
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		// add the action listener
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	// main function
	public static void main(String[] args) {
		// create the ClueGame object and set it as visible
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}
}
