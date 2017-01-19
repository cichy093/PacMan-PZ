package pz.pacman.mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pz.pacman.constants.EngineEventType;
import pz.pacman.engine.events.EngineEvent;
import pz.pacman.engine.interfaces.IManageable;
import pz.pacman.mvc.manager.MVCManager;
import pz.pacman.mvc.model.GameOverModel;
import pz.pacman.mvc.model.MainMenuModel;
import pz.pacman.mvc.view.GameOverView;
import pz.pacman.mvc.view.MainMenuView;

public class GameOverController extends	BaseController<GameOverView, GameOverModel> implements KeyListener, IManageable {

	public GameOverController(GameOverView view, GameOverModel model) {
		super(view, model);

		getView().setCompModel(model);
		getView().getMainFrame().setContentPane(getView().getMainPanel());
		getView().getMainFrame().addKeyListener(this);
		getView().getMainFrame().revalidate();
	}

	protected void executeContinueOption() {
		this.freeze();
		if (!MVCManager.getInstance().restore(MainMenuController.class)) {
			new MainMenuController(new MainMenuView(), new MainMenuModel());
			getModel().notifyEngine(new EngineEvent(EngineEventType.ABANDON_GAME));
			MVCManager.getInstance().destroy(GameFrameController.class);
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				executeContinueOption();
			break;
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
