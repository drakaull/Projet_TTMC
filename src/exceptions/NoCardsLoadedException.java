package exceptions;

public class NoCardsLoadedException extends Exception {
	public NoCardsLoadedException() {
		super("No cards loaded from json");
	}
}
