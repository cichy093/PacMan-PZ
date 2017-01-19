package pz.pacman.engine.timers;

import java.awt.event.ActionEvent;

import pz.pacman.engine.events.TimerInterruptEvent;
import pz.pacman.engine.singleton.Logger;

public class MainTimer extends BaseTimer {
	
	public MainTimer(int interval) {
		super(interval);
		Logger.getInstance().logInformation("Creating MainTimer object");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.timer) {
			this.notifyListeners(new TimerInterruptEvent<MainTimer>(MainTimer.class));
		}
	}
}
