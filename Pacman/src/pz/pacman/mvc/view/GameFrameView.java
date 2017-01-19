package pz.pacman.mvc.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.singleton.Renderer;

public class GameFrameView extends BaseView {

	private JPanel	mainPanel;

	public GameFrameView() {
		initialize();
	}

	private void initialize() {
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BorderLayout());
		
		mainPanel.setBackground(PacmanConstants.ingameBackgroundColor);
		
		mainPanel.add(Renderer.getInstance());
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
}
