package pz.pacman.engine.listeners;

public interface TimerListener {
	public void onTimerStart();
	public void onTimerStop();
	public void onTimerRestart();
	public void onIntervalChange(int interval);
}
