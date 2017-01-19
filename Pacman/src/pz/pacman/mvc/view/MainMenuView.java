package pz.pacman.mvc.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import pz.pacman.components.MainMenuComp;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.mvc.model.MainMenuModel;

public class MainMenuView extends BaseView {

	private JPanel panel;
	private MainMenuComp component;

	public MainMenuView() {
		initialize();
	}

	private void initialize() {
		this.panel = new JPanel();
		panel.setBackground(PacmanConstants.backgroundColor);

		this.component = new MainMenuComp();
		
		panel.add(this.component);

		getMainFrame().setContentPane(panel);
	}
	
	public void setCompModel(MainMenuModel model) {
		this.component.setModel(model);
	}
	
	public JPanel getMainPanel() {
		return this.panel;
	}

	public int showConfirmOnExit() {
		return PopupWindow.showConfirm("Confirm decision", "Do you want to quit?");
	}
	
	public File showFileChooser(String fileType, String typeDesc) {
		File selectedFile = null;
		
		JFileChooser fileChooser = new JFileChooser(".\\");
		FileNameExtensionFilter saveFilter = new FileNameExtensionFilter(typeDesc, fileType);
		fileChooser.addChoosableFileFilter(saveFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int chooserState = fileChooser.showOpenDialog(getMainFrame());
		if (chooserState == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
		}
		
		return selectedFile;
	}
}
