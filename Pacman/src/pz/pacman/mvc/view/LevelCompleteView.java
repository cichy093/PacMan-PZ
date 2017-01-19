package pz.pacman.mvc.view;

import javax.swing.JPanel;

import pz.pacman.components.LevelCompleteComp;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.mvc.model.LevelCompleteModel;

public class LevelCompleteView extends BaseView {

	private JPanel	mainPanel;
	private LevelCompleteComp component;

	public LevelCompleteView() {
		initialize();
	}

	private void initialize() {
		this.mainPanel = new JPanel();
		mainPanel.setBackground(PacmanConstants.backgroundColor);

		this.component = new LevelCompleteComp();
		
		mainPanel.add(this.component);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public void setCompModel(LevelCompleteModel model) {
		this.component.setModel(model);
	}
}
