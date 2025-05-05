package exceptions;

public class InvalidQuestionException extends Exception {
	public InvalidQuestionException() {
		super("Invalid question");
	}
}
