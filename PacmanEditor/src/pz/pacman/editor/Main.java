package pz.pacman.editor;

import javax.swing.SwingUtilities;

import pz.pacman.editor.engines.Logger;
import pz.pacman.editor.mvc.controller.MainWindowController;
import pz.pacman.editor.mvc.model.MainWindowModel;
import pz.pacman.editor.mvc.view.MainWindowView;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Logger.getInstance().logInformation("Creating application main frame - MainWindowController");
				new MainWindowController(new MainWindowView(), new MainWindowModel());
			}
		});
	}
}
