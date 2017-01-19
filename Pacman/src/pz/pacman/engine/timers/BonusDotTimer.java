package pz.pacman.engine.timers;

import java.awt.event.ActionEvent;

import pz.pacman.engine.events.TimerInterruptEvent;
import pz.pacman.engine.singleton.Logger;

public class BonusDotTimer extends BaseTimer {
	
	public BonusDotTimer(int interval) {
		super(interval);
		Logger.getInstance().logInformation("Creating BonusDotTimer object");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.timer) {
			this.onTimerStop();
			this.notifyListeners(new TimerInterruptEvent<BonusDotTimer>(BonusDotTimer.class));
		}
	}
}
