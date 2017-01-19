package pz.pacman.engine.timers;

import java.awt.event.ActionEvent;

import pz.pacman.engine.events.TimerInterruptEvent;
import pz.pacman.engine.singleton.Logger;

public class GameTimer extends BaseTimer {

	public GameTimer(int interval) {
		super(interval);
		Logger.getInstance().logInformation("Creating GameTimer object");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.timer) {
			this.notifyListeners(new TimerInterruptEvent<GameTimer>(GameTimer.class));
		}
	}
	
}
