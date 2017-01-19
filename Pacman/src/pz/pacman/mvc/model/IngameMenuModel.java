package pz.pacman.mvc.model;

import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.engines.GameDataEngine;
import pz.pacman.engine.singleton.Engine;


public class IngameMenuModel implements BaseModel {

	private int selectedMenuPosition;
	private GameDataEngine gameData;
	
	public IngameMenuModel() {
		this.selectedMenuPosition = 0;
		this.gameData = Engine.getInstance().getGameData();
	}

	public boolean isBonusLevel() {
		return (gameData.getLevel() == 0) ? true : false;
	}
	
	public int getSelectedMenuPosition() {
		return selectedMenuPosition;
	}

	public void decSelectedMenuPosition() {
		selectedMenuPosition = (selectedMenuPosition == 0) ? PacmanConstants.ingameMenuCnt - 1 : --selectedMenuPosition;
	}
	
	public void incSelectedMenuPosition() {
		selectedMenuPosition = (selectedMenuPosition == PacmanConstants.ingameMenuCnt - 1) ? 0 : ++selectedMenuPosition;
	}
}
