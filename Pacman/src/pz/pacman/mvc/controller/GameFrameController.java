package pz.pacman.mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pz.pacman.constants.Direction;
import pz.pacman.constants.EngineEventType;
import pz.pacman.engine.events.EngineEvent;
import pz.pacman.engine.events.FrameEvent;
import pz.pacman.engine.interfaces.IManageable;
import pz.pacman.engine.listeners.FrameListener;
import pz.pacman.engine.singleton.Engine;
import pz.pacman.mvc.manager.MVCManager;
import pz.pacman.mvc.model.GameCompleteModel;
import pz.pacman.mvc.model.GameFrameModel;
import pz.pacman.mvc.model.GameOverModel;
import pz.pacman.mvc.model.IngameMenuModel;
import pz.pacman.mvc.model.LevelCompleteModel;
import pz.pacman.mvc.view.GameCompleteView;
import pz.pacman.mvc.view.GameFrameView;
import pz.pacman.mvc.view.GameOverView;
import pz.pacman.mvc.view.IngameMenuView;
import pz.pacman.mvc.view.LevelCompleteView;

public class GameFrameController extends	BaseController<GameFrameView, GameFrameModel> implements KeyListener, FrameListener, IManageable {

	public GameFrameController(GameFrameView view, GameFrameModel model) {
		super(view, model);

		Engine.getInstance().addListener(this);
		getView().getMainFrame().setContentPane(getView().getMainPanel());
		getView().getMainFrame().addKeyListener(this);
		getView().getMainFrame().revalidate();
	}
	
	private void pauseGame() {
		getModel().notifyEngine(new EngineEvent(EngineEventType.PAUSE_GAME));
		this.freeze();
		if (!MVCManager.getInstance().restore(IngameMenuController.class)) {	
			new IngameMenuController(new IngameMenuView(), new IngameMenuModel());
		}
	}
	
	private void movePacman(Direction direction) {
		EngineEventType type = EngineEventType.PACMAN_MOVE_DOWN;
		
		switch (direction) {
			case DOWN:	type = EngineEventType.PACMAN_MOVE_DOWN;	break;
			case LEFT:	type = EngineEventType.PACMAN_MOVE_LEFT;	break;
			case RIGHT: type = EngineEventType.PACMAN_MOVE_RIGHT;	break;
			case UP:	type = EngineEventType.PACMAN_MOVE_UP;		break;
		};
		
		getModel().notifyEngine(new EngineEvent(type));
	}
	
	private void gameOver() {
		this.freeze();
		if (!MVCManager.getInstance().restore(GameOverController.class)) {
			new GameOverController(new GameOverView(), new GameOverModel());
		}
	}
	
	private void levelComplete() {
		this.freeze();
		if (!MVCManager.getInstance().restore(LevelCompleteController.class)) {
			new LevelCompleteController(new LevelCompleteView(), new LevelCompleteModel());
		}
	}
	
	private void gameComplete() {
		this.freeze();
		if (!MVCManager.getInstance().restore(GameCompleteController.class)) {
			new GameCompleteController(new GameCompleteView(), new GameCompleteModel());
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				this.pauseGame();
			break;
			
			case KeyEvent.VK_P:
				this.pauseGame();
				break;
			
			case KeyEvent.VK_UP:
				this.movePacman(Direction.UP);
			break;
			
			case KeyEvent.VK_DOWN:
				this.movePacman(Direction.DOWN);
			break;
			
			case KeyEvent.VK_LEFT:
				this.movePacman(Direction.LEFT);
			break;
			
			case KeyEvent.VK_RIGHT:
				this.movePacman(Direction.RIGHT);
			break;
		};
	}
	
	@Override
	public void OnFrameEvent(FrameEvent e) {
		switch (e.getType()) {
		case GAME_OVER: 		gameOver();			break;
		case LEVEL_COMPLETE: 	levelComplete();	break;
		case GAME_COMPLETE:		gameComplete();		break;
		default:									break;
		}
	}

	@Override
	public void restore() {
		getView().getMainFrame().setContentPane(getView().getMainPanel());
		getView().getMainFrame().addKeyListener(this);
		getView().getMainFrame().revalidate();
	}

	@Override
	public void freeze() {
		this.getView().getMainFrame().removeKeyListener(this);
		MVCManager.getInstance().freeze(this.getClass(), this, true);		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// For further features, required by KeyListener interface
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// For further features, required by KeyListener interface
	}
}
