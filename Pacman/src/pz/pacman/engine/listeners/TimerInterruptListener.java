package pz.pacman.engine.listeners;

import pz.pacman.engine.events.TimerInterruptEvent;

public interface TimerInterruptListener {
	@SuppressWarnings("rawtypes")
	public void onTimerInterrupt(TimerInterruptEvent e);
}
