package pz.pacman.editor.mvc.controller;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JOptionPane;

import pz.pacman.editor.constants.Validation;
import pz.pacman.editor.engines.Logger;
import pz.pacman.editor.mvc.model.MainWindowModel;
import pz.pacman.editor.mvc.view.MainWindowView;
import pz.pacman.editor.mvc.view.PopupWindow;

public class MainWindowController extends	BaseController<MainWindowView, MainWindowModel> implements ActionListener, MouseMotionListener, MouseListener {

	public MainWindowController(MainWindowView view, MainWindowModel model) {
		super(view, model);
		
		getView().setCompModel(model);
		
		getView().getMntmExit().addActionListener(this);
		getView().getMntmNew().addActionListener(this);
		getView().getMntmOpen().addActionListener(this);
		getView().getMntmSave().addActionListener(this);
		getView().getMntmSaveAs().addActionListener(this);
		
		getView().getComponent().addMouseMotionListener(this);
		getView().getComponent().addMouseListener(this);
		
		getView().getBtnVerifyMap().addActionListener(this);
		getView().getBtnClearMap().addActionListener(this);
	}
	
	private void executeOpenMap() {
		Logger.getInstance().logInformation("Executing OpenMap method - begin");
		if (JOptionPane.YES_OPTION == PopupWindow.showConfirm("Confirm action", "Do you want to open new file? All unsaved data will be lost.")) {
			File selectedFile = getView().showOpenFileChooser();
			
			if (selectedFile != null) {				
				Logger.getInstance().logInformation("Selected map file: "+ selectedFile.getAbsolutePath());
				if (selectedFile.exists()) {
					Logger.getInstance().logInformation("Trying to load map data");
					try {
						getModel().clear();
						getModel().loadMapFromFile(selectedFile);
						getModel().setMapPath(selectedFile.getAbsolutePath());
						getView().repaint();
					} catch (Exception e) {
						Logger.getInstance().logException(e.toString(), e.getMessage());
						PopupWindow.showError("Error", "Cannot open current file with maps data - "+ selectedFile.getName() +".");
					}
				} else {
					Logger.getInstance().logError("File "+ selectedFile.getAbsolutePath() +" doesn't exists.");
					PopupWindow.showError("Error", "File "+ selectedFile.getName() +" doesn't exists.");
				}
			}
		}
		Logger.getInstance().logInformation("Executing OpenMap method - end");
	}
	
	private void executeQuit() {
		int result = getView().showConfirmOnExit();
		if (result == JOptionPane.YES_OPTION) {
			Logger.getInstance().logInformation("End of work - exiting");
			getView().getMainFrame().dispose();
			System.exit(0);
		}
	}
	
	private void executeSave() {
		Logger.getInstance().logInformation("Executing Save method - begin");
		if (getModel().getMapPath().isEmpty()) {
			this.executeSaveAs();
		} 
		else {
			getModel().saveToFile(getModel().getMapPath());
		}
		Logger.getInstance().logInformation("Executing Save method - end");
	}
	
	private void executeSaveAs() {
		Logger.getInstance().logInformation("Executing SaveAs method - begin");
		File selectedFile = getView().showSaveFileChooser();
		
		if(selectedFile != null) {
			if (selectedFile.exists()){
				Logger.getInstance().logInformation("Selected map file: "+ selectedFile.getAbsolutePath());
	            int result = PopupWindow.showConfirm("Existing file", "The file exists, overwrite?");
	            
	            if (result == JOptionPane.YES_OPTION) {
	            	Logger.getInstance().logInformation("Trying to save map data");
	            	getModel().saveToFile(selectedFile);
	            } 
	            else {
	            	Logger.getInstance().logError("Cannot save map data");
	            	PopupWindow.show("Information", "Your map won't be save!");
	            }
			} 
			else {
				getModel().saveToFile(selectedFile);
			}
		}
		Logger.getInstance().logInformation("Executing SaveAs method - end");
	}
	
	private void executeVerify() {
		Logger.getInstance().logInformation("Executing Verify method - begin");
		if (getModel().isForbiddenSquare() == Validation.SQUARE_2X2) {
			Logger.getInstance().logWarning("Verification - forbidden square");
			PopupWindow.show("Checking Completed", "Your map isn't valid - at least one forbidden square (2x2 or bigger).");
		} 
		else {
			if (getModel().isEnoughFreeBlocks() == Validation.NOT_ENOUGH_SPACE) {
				Logger.getInstance().logWarning("Verification - not enough free blocks");
				PopupWindow.show("Checking Completed", "You map isn't valid - not enough free blocks (at least 20 required).");
			}
			else {
				Logger.getInstance().logInformation("Verification - Completed");
				PopupWindow.show("Checking Completed", "Your map is valid. You can save it to file.");
			}
		}
	}
	
	private void executeNew() {
		Logger.getInstance().logInformation("Executing New method - begin");
		if (JOptionPane.YES_OPTION == PopupWindow.showConfirm("Confirm action", "Do you want to clear entire workspace? All unsaved data will be lost.")) {
			getModel().clear();
			getView().repaint();
			getModel().setMapPath("");
		}
		Logger.getInstance().logInformation("Executing New method - end");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getView().getMntmExit()) {
			this.executeQuit();
		} 
		else if (e.getSource() == getView().getMntmNew()) {
			this.executeNew();
		}
		else if (e.getSource() == getView().getMntmOpen()) {
			this.executeOpenMap();
		}
		else if (e.getSource() == getView().getMntmSave()) {
			this.executeSave();
		}
		else if (e.getSource() == getView().getMntmSaveAs()) {
			this.executeSaveAs();
		}		
		else if (e.getSource() == getView().getBtnClearMap()) {
			this.executeNew();
		}
		else if (e.getSource() == getView().getBtnVerifyMap()) {
			this.executeVerify();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getSource() == getView().getComponent()) {
			Rectangle collisionRect = new Rectangle(e.getX(), e.getY(), 1, 1);
			if (getModel().isCollisionWithBlocks(collisionRect, getView().getRadioAddWall().isSelected())) {
				getView().getComponent().repaint();
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == getView().getComponent()) {
			Rectangle collisionRect = new Rectangle(e.getX(), e.getY(), 1, 1);
			if (getModel().isCollisionWithBlocks(collisionRect, getView().getRadioAddWall().isSelected())) {
				getView().getComponent().repaint();
			}
		}		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// Empty, for further features
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Empty, for further features
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Empty, for further features
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Empty, for further features
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Empty, for further features
	}
}
