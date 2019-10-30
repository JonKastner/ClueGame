// Authors: Jonathon Kastner and Gavin Webster
// BadConfigFormatException class

package clueGame;

public class BadConfigFormatException extends Exception{

	// default constructor
	public BadConfigFormatException() {
		System.out.println(super.getMessage());
	}
	
	// constructor with string parameter, prints the string
	public BadConfigFormatException(String s) {
		System.out.println(s);
	}
	
}
