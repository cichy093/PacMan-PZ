package pz.pacman.engine.singleton;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.element.passive.ScoreBar;
import pz.pacman.engine.engines.GameDataEngine;
import pz.pacman.engine.interfaces.IPaintable;


public class Renderer extends JComponent {
	private static final long serialVersionUID = -6947945323695086681L;

	private static Renderer instance;
	
	private GameDataEngine gameData;
	private ScoreBar scoreBar;
	
	public static Renderer getInstance() {
		if (instance == null) {
			Logger.getInstance().logInformation("Creating Renderer singleton object");
			instance = new Renderer();
		}
		
		return instance;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(PacmanConstants.windowWidth, PacmanConstants.windowHeight);
	}
	
	private Renderer() {
		this.gameData = null;
		this.scoreBar = null;
	}
	
	public void initialize(GameDataEngine gameData) {
		this.gameData = gameData;
		this.scoreBar = new ScoreBar(this.gameData);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (IPaintable x : gameData.getPaintableElements()) {
			x.paint(g);
		}
		
		this.scoreBar.paint(g);
	}
}
