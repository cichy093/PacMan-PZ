package pz.pacman.mvc.view;

import javax.swing.JPanel;

import pz.pacman.components.GameOverComp;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.mvc.model.GameOverModel;

public class GameOverView extends BaseView {

	private JPanel	mainPanel;
	private GameOverComp component;

	public GameOverView() {
		initialize();
	}

	private void initialize() {
		this.mainPanel = new JPanel();
		mainPanel.setBackground(PacmanConstants.backgroundColor);

		this.component = new GameOverComp();
		
		mainPanel.add(this.component);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public void setCompModel(GameOverModel model) {
		this.component.setModel(model);
	}
}
