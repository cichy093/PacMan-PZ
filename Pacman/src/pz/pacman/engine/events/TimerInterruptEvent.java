package pz.pacman.engine.events;

public class TimerInterruptEvent<T> {
	
	private Class<T> timerType;
	
	public TimerInterruptEvent(Class<T> timerType) {
		this.timerType = timerType;
	}
	
	public Class<T> getTimerType() {
		return timerType;
	}
}
