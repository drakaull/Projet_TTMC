package models;

import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Chronometer {
    private Timer timer; // timer object to schedule tasks at fixed intervals
    private int duration; // remaining duration in seconds
    private IntegerProperty timeSeconds; // property to observe the current time value
    private Runnable onFinish; // Action to perform when the timer finishes
    private Runnable onTick; // Action to perform every seconds

    public Chronometer() {
        this.timeSeconds = new SimpleIntegerProperty();
    }

    // Sets the difficulty level, which defines the duration
    public void setDifficulty(Difficulty difficulty) {
        difficulty.setDuration(this);
    }

    public int getDuration() {
        return duration;
    }

    // sets the total duration and updates the timeSeconds property
    public void setDuration(int duration) {
        this.duration = duration;
        this.timeSeconds.set(duration);
    }

    public IntegerProperty getTimeSeconds() {
        return timeSeconds;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    /*
     * Starts the countdown timer
     * Launches actions every second and handles when the timer finishes
     */
    public void startChrono(Runnable onTick, Runnable onFinish) {
        this.onTick = onTick;
        this.onFinish = onFinish;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                duration--;
                if (onTick != null) onTick.run(); // Execute onTick action every seconde if defined
                if (duration <= 0) { // when duration reaches zero, stop the timer and execute onFinished
                    stopChrono();
                    if (onFinish != null) onFinish.run();
                }
            }
        }, 1000, 1000);
    }

    public void stopChrono() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
} 