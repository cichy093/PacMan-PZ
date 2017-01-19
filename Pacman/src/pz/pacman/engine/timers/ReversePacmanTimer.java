package pz.pacman.engine.timers;

import java.awt.event.ActionEvent;

import pz.pacman.engine.events.TimerInterruptEvent;
import pz.pacman.engine.singleton.Logger;

public class ReversePacmanTimer extends BaseTimer {

	public ReversePacmanTimer(int interval) {
		super(interval);
		Logger.getInstance().logInformation("Creating ReversePacman object");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.timer) {
			this.onTimerStop();
			this.notifyListeners(new TimerInterruptEvent<ReversePacmanTimer>(ReversePacmanTimer.class));
		}
	}

}
