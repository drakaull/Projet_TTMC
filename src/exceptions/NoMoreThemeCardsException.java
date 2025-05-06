package exceptions;

public class NoMoreThemeCardsException extends Exception {
	public NoMoreThemeCardsException() {
		super("No more cards available for this theme");
	}
}
