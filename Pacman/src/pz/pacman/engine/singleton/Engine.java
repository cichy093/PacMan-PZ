package pz.pacman.engine.singleton;

import java.util.LinkedList;

import pz.pacman.constants.Direction;
import pz.pacman.constants.EngineEventType;
import pz.pacman.constants.FrameEventType;
import pz.pacman.constants.SchedulerEventType;
import pz.pacman.engine.engines.CollisionEngine;
import pz.pacman.engine.engines.GameDataEngine;
import pz.pacman.engine.events.EngineEvent;
import pz.pacman.engine.events.FrameEvent;
import pz.pacman.engine.events.LoadSaveEvent;
import pz.pacman.engine.events.SchedulerEvent;
import pz.pacman.engine.interfaces.IMoveable;
import pz.pacman.engine.listeners.EngineListener;
import pz.pacman.engine.listeners.FrameListener;
import pz.pacman.engine.listeners.LoadSaveListener;
import pz.pacman.engine.listeners.SchedulerListener;

public class Engine implements EngineListener, LoadSaveListener {
	
	private static Engine instance;
	private GameDataEngine gameData;

	private SchedulerListener schedulerListener;
	private LinkedList<FrameListener> frameListeners;
	
	public static Engine getInstance() {
		if (instance == null) {
			Logger.getInstance().logInformation("Creating Engine singleton object");
			instance = new Engine();
		}
		
		return instance;
	}
	
	protected Engine() {
		this.frameListeners = new LinkedList<FrameListener>();
	}
	
	public GameDataEngine getGameData() {
		return this.gameData;
	}
	
	public void addListener(SchedulerListener listener) {
		this.schedulerListener = listener;
	}
	
	public void addListener(FrameListener listener) {
		this.frameListeners.add(listener);
	}
	
	public void notifyListener(SchedulerEventType type) {
		SchedulerEvent e = new SchedulerEvent(type);
		schedulerListener.OnEvent(e);
	}
	
	@SuppressWarnings("unchecked")
	public void notifyListener(FrameEventType type) {
		FrameEvent e = new FrameEvent(type);
		System.out.println(this.frameListeners.size());
		
		Object clone = this.frameListeners.clone();
		LinkedList<FrameListener> tmp = (LinkedList<FrameListener>) clone;			
		
		for (FrameListener x : tmp) {
			x.OnFrameEvent(e);
		}
	}
	
	public void initialize() {
		this.gameData = new GameDataEngine();
		
		new Thread(new Runnable() {

            @Override
            public void run() {
            	Scheduler.getInstance().initialize();
            }
        }).start();
		
		Renderer.getInstance().initialize(this.gameData);
		CollisionEngine.getInstance().initialize(this.gameData);
		
		this.gameData.addListener(Engine.getInstance());
		this.gameData.addListener(Scheduler.getInstance());
		this.addListener(Scheduler.getInstance());
	}

	public void startNewGame() {
		gameData.createNewGame();
		this.notifyListener(SchedulerEventType.START_GAME);
	}
	
	public void gameOver() {
		this.notifyListener(SchedulerEventType.END_GAME);
		this.notifyListener(FrameEventType.GAME_OVER);
	}
	
	public void levelComplete() {
		this.notifyListener(SchedulerEventType.END_GAME);
		this.notifyListener(FrameEventType.LEVEL_COMPLETE);
	}
	
	public void gameComplete() {
		this.notifyListener(SchedulerEventType.END_GAME);
		this.notifyListener(FrameEventType.GAME_COMPLETE);
	}
	
	public void loadNextLevel() {
		this.notifyListener(SchedulerEventType.START_GAME);
		gameData.createNextLevel();
	}
	
	public void loadBonusLevel() {
		this.notifyListener(SchedulerEventType.START_GAME);
		this.notifyListener(FrameEventType.START_GAME);
	}
	
	public void resumeSavedGame() {
		this.notifyListener(SchedulerEventType.START_GAME);
		this.notifyListener(FrameEventType.RESUME_SAVED_GAME);
	}
	
	public void moveElements() {
		for (IMoveable x : this.gameData.getMoveableElements()) {
			x.move();
		}
		
		CollisionEngine.getInstance().checkCollisions();
	}
	
	public void stopGame() {
		this.notifyListener(SchedulerEventType.STOP_GAME);
	}
	
	public void pauseGame() {
		this.notifyListener(SchedulerEventType.PAUSE_GAME);
	}
	
	public void resumeGame() {
		this.notifyListener(SchedulerEventType.RESUME_GAME);
	}
	
	public void endGame() {
		this.notifyListener(SchedulerEventType.END_GAME);
		this.gameData.destroyData();
	}

	private void movePacman(Direction direction) {
		this.gameData.getPacman().setRequiredDirection(direction);
	}
	
	private void incGameTime() {
		this.gameData.addTime();
	}
	
	private void removeBonusDot() {
		this.gameData.removeBonusDot();
	}
	
	private void revertGameMode() {
		this.gameData.revertGameMode();
	}
	
	private void shuffleDot() {
		this.gameData.shuffleDot();
	}
	
	@Override
	public void OnEvent(EngineEvent e) {
		EngineEventType type = e.getType();
		
		switch(type) {		
			case START_NEW_GAME: 	startNewGame();					break;	
			case RESUME_SAVED_GAME: resumeSavedGame();				break;
			case PAUSE_GAME:		pauseGame();					break;
			case RESUME_GAME:		resumeGame();					break;
			case GAME_OVER:			gameOver();						break;
			case ABANDON_GAME:		endGame();						break;
			case LEVEL_COMPLETE:	levelComplete();				break;
			case GAME_COMPLETE:		gameComplete();					break;
			case LOAD_NEXT_LEVEL:	loadNextLevel();				break;
			case MOVE_ELEMENS:		moveElements();					break;
			case PACMAN_MOVE_UP:	movePacman(Direction.UP);		break;
			case PACMAN_MOVE_DOWN:	movePacman(Direction.DOWN);		break;
			case PACMAN_MOVE_LEFT:	movePacman(Direction.LEFT);		break;
			case PACMAN_MOVE_RIGHT:	movePacman(Direction.RIGHT);	break;
			case INC_GAME_TIME:		incGameTime();					break;
			case BONUS_DOT_DESTROY:	removeBonusDot();				break;
			case REVERT_PACMAN:		revertGameMode();				break;
			case SHUFFLE_DOT:		shuffleDot();					break;
			}
	}

	@Override
	public void OnLoadSaveEvent(LoadSaveEvent e) {
		switch (e.getMode()) {
		case LOAD:				gameData.loadGameState(e.getPath());	break;
		case LOAD_BONUS_LEVEL:	gameData.loadBonusMap(e.getPath());		break;
		case SAVE:				gameData.saveGameState(e.getPath());	break;
		}
	}
}
