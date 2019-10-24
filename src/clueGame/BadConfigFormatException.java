package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		System.out.println(super.getMessage());
	}
	
	public BadConfigFormatException(String s) {
		System.out.println(s);
	}
	
}
