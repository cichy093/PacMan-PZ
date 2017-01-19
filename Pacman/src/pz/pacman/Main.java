package  pz.pacman;

import javax.swing.SwingUtilities;

import pz.pacman.engine.singleton.Engine;
import pz.pacman.engine.singleton.Logger;
import pz.pacman.mvc.controller.MainMenuController;
import pz.pacman.mvc.manager.MVCManager;
import pz.pacman.mvc.model.MainMenuModel;
import pz.pacman.mvc.view.MainMenuView;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Logger.getInstance();

				Engine.getInstance().initialize();
				MVCManager.getInstance().initialize();
				
				Logger.getInstance().logInformation("Creating MainMenuController, View & Model objects");
				MainMenuController controller = new MainMenuController(new MainMenuView(), new MainMenuModel());
				Engine.getInstance().addListener(controller);
			}
		});
	}
}
