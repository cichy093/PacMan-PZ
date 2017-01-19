package pz.pacman.mvc.model;

import java.util.LinkedList;

import pz.pacman.engine.engines.GameDataEngine;
import pz.pacman.engine.entities.TopScorerData;
import pz.pacman.engine.singleton.Engine;

public class TopScoresModel implements BaseModel {
	
	private GameDataEngine gameData;
	
	public TopScoresModel() {
		this.gameData = Engine.getInstance().getGameData();
	}

	public LinkedList<TopScorerData> getTopScorers() {
		return this.gameData.getTopScores();
	}
}
