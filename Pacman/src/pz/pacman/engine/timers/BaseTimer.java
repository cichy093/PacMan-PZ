package pz.pacman.engine.timers;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import pz.pacman.engine.events.TimerInterruptEvent;
import pz.pacman.engine.listeners.TimerListener;
import pz.pacman.engine.listeners.TimerInterruptListener;
import pz.pacman.engine.singleton.Scheduler;

public abstract class BaseTimer implements ActionListener, TimerListener {
	
	protected Timer timer;
	protected boolean isEnabled;
	protected TimerInterruptListener listener;
	
	public BaseTimer(int interval) {
		this.timer = new Timer(interval, this);
		this.isEnabled = false;
		this.listener = Scheduler.getInstance();
	}
	
	public void startTimer() {
		this.isEnabled = true;
		timer.start();
	}
	
	public void stopTimer() {
		this.isEnabled = false;
		timer.stop();
	}
	
	@SuppressWarnings("rawtypes")
	protected void notifyListeners(TimerInterruptEvent e) {
		this.listener.onTimerInterrupt(e);
	}
	
	@Override
	public void onTimerStart() {
		this.isEnabled = true;
		timer.start();
	}
	
	@Override
	public void onTimerStop() {
		this.isEnabled = false;
		timer.stop();
	}
	
	public void onTimerPause() {
		timer.stop();
	}
	
	public void onTimerResume() {
		if (this.isEnabled == true) {
			onTimerStart();
		}
	}
	
	@Override
	public void onTimerRestart() {
		this.isEnabled = false;
		timer.restart();
		this.isEnabled = true;
	}
	
	@Override
	public void onIntervalChange(int interval) {
		this.isEnabled = false;
		timer.stop();
		timer.setDelay(interval);
	}
}
