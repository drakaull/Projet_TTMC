package models;

import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Chronometer {
    private Timer timer;
    private int duration;
    private IntegerProperty timeSeconds;
    private Runnable onFinish;
    private Runnable onTick;

    public Chronometer() {
        this.timeSeconds = new SimpleIntegerProperty();
    }

    public void setDifficulty(Difficulty difficulty) {
        difficulty.setDuration(this);
    }

    public int getDuration() {
        return duration;
    }

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

    public void startChrono(Runnable onTick, Runnable onFinish) {
        this.onTick = onTick;
        this.onFinish = onFinish;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                duration--;
                if (onTick != null) onTick.run();
                if (duration <= 0) {
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