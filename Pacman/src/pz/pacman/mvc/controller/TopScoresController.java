package pz.pacman.mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pz.pacman.engine.interfaces.IManageable;
import pz.pacman.mvc.manager.MVCManager;
import pz.pacman.mvc.model.TopScoresModel;
import pz.pacman.mvc.view.TopScoresView;

public class TopScoresController extends BaseController<TopScoresView, TopScoresModel> implements KeyListener, IManageable {

	public TopScoresController(TopScoresView view, TopScoresModel model) {
		super(view, model);

		getView().setCompModel(getModel());
		getView().getMainFrame().setContentPane(getView().getMainPanel());
		getView().getMainFrame().addKeyListener(this);
		getView().getMainFrame().revalidate();
	}

	private void executeQuitButtonEvent() {
		this.freeze();
		MVCManager.getInstance().restore(MainMenuController.class);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				this.executeQuitButtonEvent();
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
