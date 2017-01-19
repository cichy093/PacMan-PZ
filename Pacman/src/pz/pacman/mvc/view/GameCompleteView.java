package pz.pacman.mvc.view;

import javax.swing.JPanel;

import pz.pacman.components.GameCompleteComp;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.mvc.model.GameCompleteModel;

public class GameCompleteView extends BaseView {

	private JPanel	mainPanel;
	private GameCompleteComp component;

	public GameCompleteView() {
		initialize();
	}

	private void initialize() {
		this.mainPanel = new JPanel();
		mainPanel.setBackground(PacmanConstants.backgroundColor);

		this.component = new GameCompleteComp();
		
		mainPanel.add(this.component);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public void setCompModel(GameCompleteModel model) {
		this.component.setModel(model);
	}
}
