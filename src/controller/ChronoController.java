package controller;

import models.Chronometer;
import models.Difficulty;

public class ChronoController {
    private Chronometer chronometer;

    // Constructor initializes the chronometer instance
    public ChronoController() {
        this.chronometer = new Chronometer();
    }

    // Applies the selected difficulty to the chronometer (affects countdown duration)
    public void applyDifficulty(Difficulty difficulty) {
        chronometer.setDifficulty(difficulty);
    }

    // Starts the chronometer, accepting callbacks for tick events and when the timer finishes
    public void startChrono(Runnable onTick, Runnable onFinish) {
        chronometer.startChrono(onTick, onFinish);
    }

    // Stops the chronometer if it is currently running
    public void stopChrono() {
        if (chronometer.getTimer() != null) {
            chronometer.getTimer().cancel();
        }
    }

    // Provides access to the internal Chronometer instance
    public Chronometer getChronometer() {
        return chronometer;
    }
}
