package pz.pacman.mvc.model;

import pz.pacman.engine.engines.GameDataEngine;
import pz.pacman.engine.singleton.Engine;

public class GameCompleteModel implements BaseModel {
	
	private GameDataEngine gameData;
	
	public GameCompleteModel() {
		this.gameData = Engine.getInstance().getGameData();
	}

	public int getLifes() {
		return gameData.getLifes();
	}
	
	public int getTotalTime() {
		return gameData.getTotalTime();
	}
	
	public int getTotalPoints() {
		return gameData.getTotalPoints();
	}

	public boolean isResultInTopScore() {
		return gameData.isTopScore();
	}
	
	public void addTopScore(String nickname) {
		gameData.addTopScore(nickname);
	}
}
