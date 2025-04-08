package controller;

import models.Chronometer;
import models.Difficulty;

public class ChronoController {
	private Chronometer chronometer;
	
	public ChronoController() {
		this.chronometer = new Chronometer();
	}
	
	public void applyDifficulty(Difficulty difficulty) {
		chronometer.setDifficulty(difficulty);
	}
	
	public void startChrono(Runnable onTick, Runnable onFinish) {
		chronometer.startChrono(onTick, onFinish);
	}
	
	public void stopChrono() {
		if (chronometer.getTimer() != null) {
			chronometer.getTimer().cancel();
		}
	}
	
	public Chronometer getChronometer() {
		return chronometer;
	}
}
