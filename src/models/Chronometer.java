package models;

import java.util.Timer;
import java.util.TimerTask;

public class Chronometer {
	private Timer timer;
	private int timeLeft; // in seconds
	private Runnable onFinish;
	private Runnable onTick;

	public Chronometer(int duration) {
		this.timeLeft = duration;
	}

	public void startChrono(Runnable onTick, Runnable onFinish) {
		this.onTick = onTick;
		this.onFinish = onFinish;

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				timeLeft--;
				if (onTick != null)
					onTick.run();
				if (timeLeft <= 0) {
					stopChrono();
					if (onFinish != null)
						onFinish.run();
				}
			}
		}, 1000, 000);	//each second
	}

	public void stopChrono() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public int getTimeLeft() {
		return timeLeft;
	}
}
