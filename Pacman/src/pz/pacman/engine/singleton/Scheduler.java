package pz.pacman.engine.singleton;

import pz.pacman.constants.EngineEventType;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.constants.SchedulerEventType;
import pz.pacman.engine.events.EngineEvent;
import pz.pacman.engine.events.SchedulerEvent;
import pz.pacman.engine.events.TimerInterruptEvent;
import pz.pacman.engine.listeners.SchedulerListener;
import pz.pacman.engine.listeners.TimerInterruptListener;
import pz.pacman.engine.timers.BonusDotTimer;
import pz.pacman.engine.timers.GameTimer;
import pz.pacman.engine.timers.MainTimer;
import pz.pacman.engine.timers.ReversePacmanTimer;

public class Scheduler implements TimerInterruptListener, SchedulerListener {
	
	private static Scheduler instance;
	
	private MainTimer mainTimer;
	private GameTimer gameTimer;
	private BonusDotTimer bonusDotTimer;
	private ReversePacmanTimer reversePacmanTimer;
	
	public static Scheduler getInstance() {
		if (instance == null) {
			Logger.getInstance().logInformation("Creating Scheduler singleton object");
			instance = new Scheduler();
		}
		
		return instance;
	}
	
	public void initialize() {
		this.mainTimer = new MainTimer(PacmanConstants.mainTimerInterval);
		this.gameTimer = new GameTimer(PacmanConstants.gameTimerInterval);
		this.bonusDotTimer = new BonusDotTimer(PacmanConstants.bonusDotTimerInterval);
		this.reversePacmanTimer = new ReversePacmanTimer(PacmanConstants.reversePacmanTimerInterval);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onTimerInterrupt(TimerInterruptEvent e) {
		if (e.getTimerType().equals(MainTimer.class)) {
			Engine.getInstance().OnEvent(new EngineEvent(EngineEventType.MOVE_ELEMENS));
			Engine.getInstance().OnEvent(new EngineEvent(EngineEventType.SHUFFLE_DOT));
			Renderer.getInstance().repaint();
		}
		else if (e.getTimerType().equals(GameTimer.class)) {
			Engine.getInstance().OnEvent(new EngineEvent(EngineEventType.INC_GAME_TIME));
		} 
		else if (e.getTimerType().equals(BonusDotTimer.class)) {
			Engine.getInstance().OnEvent(new EngineEvent(EngineEventType.BONUS_DOT_DESTROY));
			bonusDotTimer.stopTimer();
		} 
		else if (e.getTimerType().equals(ReversePacmanTimer.class)) {
			Engine.getInstance().OnEvent(new EngineEvent(EngineEventType.REVERT_PACMAN));
			reversePacmanTimer.stopTimer();
		}
	}

	private void startGame() {
		mainTimer.onTimerStart();
		gameTimer.onTimerStart();
	}
	
	private void stopGame() {
		mainTimer.onTimerPause();
		gameTimer.onTimerPause();
		bonusDotTimer.onTimerPause();
		reversePacmanTimer.onTimerPause();
	}
	
	private void pauseGame() {
		mainTimer.onTimerPause();
		gameTimer.onTimerPause();
		bonusDotTimer.onTimerPause();
		reversePacmanTimer.onTimerPause();
	}
	
	private void resumeGame() {
		mainTimer.onTimerResume();
		gameTimer.onTimerResume();
		bonusDotTimer.onTimerResume();
		reversePacmanTimer.onTimerResume();
	}
	
	private void endGame() {
		mainTimer.onTimerStop();
		gameTimer.onTimerStop();
		bonusDotTimer.onTimerStop();
		reversePacmanTimer.onTimerStop();
	}
	
	private void startRevMode() {
		reversePacmanTimer.onTimerStart();
	}
	
	private void startBonusDot() {
		bonusDotTimer.startTimer();
	}
	
	@Override
	public void OnEvent(SchedulerEvent e) {
		SchedulerEventType type = e.getType();
		switch (type) {
			case START_GAME:			startGame(); 	break;
			case STOP_GAME:				stopGame(); 	break;
			case PAUSE_GAME:			pauseGame();	break;
			case RESUME_GAME:			resumeGame();	break;
			case END_GAME:				endGame();	 	break;
			case START_REVERSE_MODE:	startRevMode();	break;
			case START_BONUS_DOT:		startBonusDot();break;
		}
	}
}
