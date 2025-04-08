package models;

public class DifficultyEasy implements Difficulty {
	@Override
	public void setDuration(Chronometer chrono) {
		chrono.setDuration(30);
	}
}
