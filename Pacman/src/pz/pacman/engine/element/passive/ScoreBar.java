package pz.pacman.engine.element.passive;

import java.awt.Color;
import java.awt.Graphics;
import java.text.SimpleDateFormat;

import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.engines.GameDataEngine;
import pz.pacman.engine.interfaces.IPaintable;

public class ScoreBar implements IPaintable {
	private GameDataEngine gameData;
	
	public ScoreBar(GameDataEngine source) {
		this.gameData = source;
	}

	@Override
	public void paint(Graphics graph) {
		graph.setColor(Color.WHITE);
		graph.drawString("POINTS: " + gameData.getPoints(), 10, PacmanConstants.scoreBarYCoord);
		graph.drawString("TIME: " + new SimpleDateFormat("mm:ss.SSS").format((long)(gameData.getTime())), 100, PacmanConstants.scoreBarYCoord);
		graph.drawString("DOTS LEFT: " + gameData.getDotsLeft(), 200, PacmanConstants.scoreBarYCoord);		
		graph.drawString("LIFES LEFT: " + gameData.getLifes(),  320, PacmanConstants.scoreBarYCoord);
	}
	
}
package pz.pacman.engine.element.passive;

public class ScoreBar {

}
