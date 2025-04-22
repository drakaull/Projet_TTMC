package exceptions;

public class WrongMusicPath extends Exception{
	public WrongMusicPath() {
		super("The path to find the music is incorrect");
	}
}
