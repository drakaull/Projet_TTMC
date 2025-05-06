package exceptions;

public class InvalidGameException extends Exception {
	public InvalidGameException() {
		super("Invalid difficulty");
	}
}
