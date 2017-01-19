package pz.pacman.mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pz.pacman.constants.EngineEventType;
import pz.pacman.engine.events.EngineEvent;
import pz.pacman.engine.interfaces.IManageable;
import pz.pacman.mvc.manager.MVCManager;
import pz.pacman.mvc.model.LevelCompleteModel;
import pz.pacman.mvc.view.LevelCompleteView;

public class LevelCompleteController extends	BaseController<LevelCompleteView, LevelCompleteModel> implements KeyListener, IManageable {

	public LevelCompleteController(LevelCompleteView view, LevelCompleteModel model) {
		super(view, model);

		getView().setCompModel(model);
		getView().getMainFrame().setContentPane(getView().getMainPanel());
		getView().getMainFrame().addKeyListener(this);
		getView().getMainFrame().revalidate();
	}

	private void executeContinueOption() {
		getModel().notifyEngine(new EngineEvent(EngineEventType.LOAD_NEXT_LEVEL));
		this.freeze();
		MVCManager.getInstance().restore(GameFrameController.class);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				this.executeContinueOption();
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
