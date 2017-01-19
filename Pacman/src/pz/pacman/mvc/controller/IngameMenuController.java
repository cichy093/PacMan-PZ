package pz.pacman.mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JOptionPane;

import pz.pacman.constants.EngineEventType;
import pz.pacman.constants.LoadSaveMode;
import pz.pacman.engine.events.EngineEvent;
import pz.pacman.engine.events.LoadSaveEvent;
import pz.pacman.engine.interfaces.IManageable;
import pz.pacman.mvc.manager.MVCManager;
import pz.pacman.mvc.model.IngameMenuModel;
import pz.pacman.mvc.view.IngameMenuView;
import pz.pacman.mvc.view.PopupWindow;

public class IngameMenuController extends	BaseController<IngameMenuView, IngameMenuModel> implements KeyListener, IManageable {

	public IngameMenuController(IngameMenuView view, IngameMenuModel model) {
		super(view, model);

		getView().setCompModel(model);
		getView().getMainFrame().setContentPane(getView().getMainPanel());
		getView().getMainFrame().addKeyListener(this);
		getView().getMainFrame().revalidate();
	}

	private void executeSelectedMenuOption() {
		switch (getModel().getSelectedMenuPosition()) {
			case 0: 	executeResumeGameOption();		break;
			case 1:		executeSaveGameOption();		break;
			case 2:		executeEndGame();				break;
		}
	}
	
	private void executeEndGame() {
		int result = getView().showConfirmOnExit();
		
		if (result == JOptionPane.YES_OPTION) {
			this.freeze();
			getModel().notifyEngine(new EngineEvent(EngineEventType.ABANDON_GAME));
			MVCManager.getInstance().destroy(GameFrameController.class);
			MVCManager.getInstance().restore(MainMenuController.class);		
		}
	}

	private void executeResumeGameOption() {
		this.freeze();
		getModel().notifyEngine(new EngineEvent(EngineEventType.RESUME_GAME));
		MVCManager.getInstance().restore(GameFrameController.class);
	}
	
	private void saveToFile(File file) {
		String path = file.getAbsolutePath();
		if (path.indexOf(".save", path.lastIndexOf("/")) < 0) {
			path += ".save";
		}
		
		try {
			getModel().notifyEngine(new LoadSaveEvent(LoadSaveMode.SAVE, path));
		} catch (Exception e) {
			//cos sie zjebalo
		}
	}
	
	private void executeSaveGameOption() {
		if (getModel().isBonusLevel()) {
			PopupWindow.show("Information", "Cannot save game state - you're in bonus level mode\n(saving possible only in casual mode).");
		} 
		else {
			File selectedFile = getView().showFileChooser();
			
			if(selectedFile != null) {
				if (selectedFile.exists()){
		            int result = PopupWindow.showConfirm("Existing file", "The file exists, overwrite?");
		            
		            if (result == JOptionPane.YES_OPTION) {
		            	saveToFile(selectedFile);
		            } else {
		            	PopupWindow.show("Information", "Your game state won't be save!");
		            }
				} else {
					saveToFile(selectedFile);
				}
			}
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
