package pz.pacman.mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.interfaces.IManageable;
import pz.pacman.mvc.manager.MVCManager;
import pz.pacman.mvc.model.GameCompleteModel;
import pz.pacman.mvc.view.GameCompleteView;
import pz.pacman.mvc.view.PopupWindow;

public class GameCompleteController extends	BaseController<GameCompleteView, GameCompleteModel> implements KeyListener, IManageable {

	public GameCompleteController(GameCompleteView view, GameCompleteModel model) {
		super(view, model);

		getView().setCompModel(model);
		getView().getMainFrame().setContentPane(getView().getMainPanel());
		getView().getMainFrame().addKeyListener(this);
		getView().getMainFrame().revalidate();
		
		topScoreInputWindow();
	}
	
	protected void topScoreInputWindow() {
		if (getModel().isResultInTopScore()) {
			String nickname;
			nickname = PopupWindow.inputDialog(getView().getMainFrame(), "Congratz!", "You've reached TOP5 scores with your score!\nEnter your nickname: ");
			while (nickname != null && (nickname.length() <= 0 || nickname.length() > PacmanConstants.maxDigitsInNickname)) {
				nickname = PopupWindow.inputDialog(getView().getMainFrame(), "Congratz!", "Min length of nickname is 1, max - "+ PacmanConstants.maxDigitsInNickname +".\n");
			}
			
			if (nickname == null) {
				PopupWindow.show("Information", "Your score won't be add to top scores board.");
			} else {
				getModel().addTopScore(nickname);
				PopupWindow.show("Information", "Your score has been added to the board!");
			}
		}
	}

	private void executeContinueOption() {
		this.freeze();
		MVCManager.getInstance().restore(MainMenuController.class);
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
