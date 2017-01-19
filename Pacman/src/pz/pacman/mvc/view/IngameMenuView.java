package pz.pacman.mvc.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import pz.pacman.components.IngameMenuComp;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.mvc.model.IngameMenuModel;

public class IngameMenuView extends BaseView {

	private JPanel panel;
	private IngameMenuComp component;

	public IngameMenuView() {
		initialize();
	}

	private void initialize() {
		this.panel = new JPanel();
		panel.setBackground(PacmanConstants.backgroundColor);

		this.component = new IngameMenuComp();
		
		panel.add(this.component);

		getMainFrame().setContentPane(panel);
	}
	
	public void setCompModel(IngameMenuModel model) {
		this.component.setModel(model);
	}
	
	public JPanel getMainPanel() {
		return this.panel;
	}
	
	public int showConfirmOnExit() {
		return PopupWindow.showConfirm("Confirm decision", "Do you want to abandon current game?\nAll unsaved data will be lost.");
	}
	
	public File showFileChooser() {
		File selectedFile = null;
		
		JFileChooser fileChooser = new JFileChooser(".\\");
		FileNameExtensionFilter saveFilter = new FileNameExtensionFilter("Pacman save (*.save)", "save");
		
		fileChooser.addChoosableFileFilter(saveFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int chooserState = fileChooser.showSaveDialog(getMainFrame());
		if (chooserState == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
		}
		
		return selectedFile;
	}
}
