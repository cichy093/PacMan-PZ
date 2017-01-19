package pz.pacman.mvc.model;

import pz.pacman.engine.engines.GameDataEngine;
import pz.pacman.engine.singleton.Engine;



public class LevelCompleteModel implements BaseModel {
	
	private GameDataEngine gameData;
	
	public LevelCompleteModel() {
		this.gameData = Engine.getInstance().getGameData();
	}
	
	public int getTime() {
		return gameData.getTime();
	}
	
	public int getTotalTime() {
		return gameData.getTotalTime();
	}
	
	public int getPoints() {
		return gameData.getPoints();
	}
	
	public int getTotalPoints() {
		return gameData.getTotalPoints();
	}
}
