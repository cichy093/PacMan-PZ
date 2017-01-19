package pz.pacman.mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JOptionPane;

import pz.pacman.constants.EngineEventType;
import pz.pacman.constants.LoadSaveMode;
import pz.pacman.engine.events.EngineEvent;
import pz.pacman.engine.events.FrameEvent;
import pz.pacman.engine.events.LoadSaveEvent;
import pz.pacman.engine.interfaces.IManageable;
import pz.pacman.engine.listeners.FrameListener;
import pz.pacman.engine.singleton.Engine;
import pz.pacman.engine.singleton.Logger;
import pz.pacman.mvc.manager.MVCManager;
import pz.pacman.mvc.model.GameFrameModel;
import pz.pacman.mvc.model.MainMenuModel;
import pz.pacman.mvc.model.TopScoresModel;
import pz.pacman.mvc.view.GameFrameView;
import pz.pacman.mvc.view.MainMenuView;
import pz.pacman.mvc.view.PopupWindow;
import pz.pacman.mvc.view.TopScoresView;

public class MainMenuController extends	BaseController<MainMenuView, MainMenuModel> implements KeyListener, IManageable, FrameListener {

	public MainMenuController(MainMenuView view, MainMenuModel model) {
		super(view, model);

		getView().setCompModel(model);
		getView().getMainFrame().addKeyListener(this);
	}

	private void executeSelectedMenuOption() {
		switch (getModel().getSelectedMenuPosition()) {
			case 0: 	executeStartNewGameOption();	break;
			case 1:		executePlayBonusLevel();		break;
			case 2:		executeLoadGameOption();		break;
			case 3:		executeTopScoresOption();		break;
			case 4:		executeQuitOption();			break;
		}
	}
	
	private void executeStartNewGameOption() {
		Logger.getInstance().logInformation("Starting new game");
		
		this.freeze();
		if (!MVCManager.getInstance().restore(GameFrameController.class)) {
			new GameFrameController(new GameFrameView(), new GameFrameModel());
		}
		Engine.getInstance().OnEvent(new EngineEvent(EngineEventType.START_NEW_GAME));
	}
	
	private void executePlayBonusLevel() {
		File selectedFile = getView().showFileChooser("map", "Pacman Bonus Map (*.map)");
		
		if (selectedFile != null) {
			if (selectedFile.exists()) {
				try {
					getModel().notifyEngine(new LoadSaveEvent(LoadSaveMode.LOAD_BONUS_LEVEL, selectedFile.getAbsolutePath()));
				} catch (Exception e) {
					PopupWindow.show("Information", "Bonus level won't be load.");
				}
			} else {
				PopupWindow.show("Information", "File doesn't exists. Bonus level won't be load!");
			}
		}
	}
	
	private void executeLoadGameOption() {
		File selectedFile = getView().showFileChooser("save", "Pacman Save (*.save)");
		
		if (selectedFile != null) {
			if (selectedFile.exists()) {
				try {
					getModel().notifyEngine(new LoadSaveEvent(LoadSaveMode.LOAD, selectedFile.getAbsolutePath()));
				} catch (Exception e) {
					PopupWindow.show("Information", "Previous game data will not be load.");
				}
			} else {
				PopupWindow.show("Information", "File doesn't exists. Game won't be load!");
			}
		}
	}
	
	private void executeTopScoresOption() {
		this.freeze();
		if (!MVCManager.getInstance().restore(TopScoresController.class)) {
			new TopScoresController(new TopScoresView(), new TopScoresModel());			
		}
	}
	
	private void executeQuitOption() {
		int result = getView().showConfirmOnExit();
		if (result == JOptionPane.YES_OPTION) {
			getView().getMainFrame().dispose();
			System.exit(0);
		}
	}
	
	private void resumeSavedGame() {
		this.freeze();
		if (!MVCManager.getInstance().restore(GameFrameController.class)) {
			GameFrameController gameFrame = new GameFrameController(new GameFrameView(), new GameFrameModel());
			Engine.getInstance().addListener(gameFrame);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {	
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				getModel().decSelectedMenuPosition();
				getView().repaint();
			break;
				
			case KeyEvent.VK_DOWN:
				getModel().incSelectedMenuPosition();
				getView().repaint();
			break;
				
			case KeyEvent.VK_ENTER:
				this.executeSelectedMenuOption();
			break;
			
			case KeyEvent.VK_ESCAPE:
				this.executeQuitOption();
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

	
	private void startBonusGame() {
		Logger.getInstance().logInformation("Starting bonus level");
		this.freeze();
		if (!MVCManager.getInstance().restore(GameFrameController.class)) {
			new GameFrameController(new GameFrameView(), new GameFrameModel());
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void OnFrameEvent(FrameEvent e) {
		switch (e.getType()) {
		case RESUME_SAVED_GAME: resumeSavedGame();	break;
		case START_GAME:		startBonusGame();	break;
		}
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
