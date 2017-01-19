package pz.pacman.mvc.model;

import pz.pacman.engine.engines.GameDataEngine;
import pz.pacman.engine.singleton.Engine;

public class GameOverModel implements BaseModel {
	
	private GameDataEngine gameData;
	
	public GameOverModel() {
		this.gameData = Engine.getInstance().getGameData();
	}
	
	public int getTotalTime() {
		return gameData.getTotalTime();
	}
	
	public int getTotalPoints() {
		return gameData.getTotalPoints();
	}
}
