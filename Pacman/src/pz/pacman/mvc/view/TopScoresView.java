package pz.pacman.mvc.view;

import javax.swing.JPanel;

import pz.pacman.components.TopScoresComp;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.mvc.model.TopScoresModel;

public class TopScoresView extends BaseView {

	private JPanel	mainPanel;
	private TopScoresComp component;

	public TopScoresView() {
		initialize();
	}

	private void initialize() {
		this.mainPanel = new JPanel();
		mainPanel.setBackground(PacmanConstants.backgroundColor);

		this.component = new TopScoresComp();
		
		mainPanel.add(this.component);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public void setCompModel(TopScoresModel model) {
		this.component.setModel(model);
	}
}
