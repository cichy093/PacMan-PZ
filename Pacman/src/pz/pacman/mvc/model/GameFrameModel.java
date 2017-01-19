package pz.pacman.mvc.model;

import pz.pacman.engine.engines.GameDataEngine;
import pz.pacman.engine.singleton.Engine;

public class GameFrameModel implements BaseModel {
	
	private GameDataEngine gameData;
	
	public GameFrameModel() {
		this.gameData = Engine.getInstance().getGameData();
	}
	
	public int getLifes() {
		return gameData.getLifes();
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

	public int getDotsLeft() {
		return gameData.getDotsLeft();
	}
}
