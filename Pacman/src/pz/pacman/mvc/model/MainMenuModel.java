package pz.pacman.mvc.model;

import pz.pacman.constants.PacmanConstants;


public class MainMenuModel implements BaseModel {

	private int selectedMenuPosition;

	public MainMenuModel() {
		this.selectedMenuPosition = 0;
	}

	public int getSelectedMenuPosition() {
		return selectedMenuPosition;
	}

	public void decSelectedMenuPosition() {
		selectedMenuPosition = (selectedMenuPosition == 0) ? PacmanConstants.mainMenuCnt - 1 : --selectedMenuPosition;
	}
	
	public void incSelectedMenuPosition() {
		selectedMenuPosition = (selectedMenuPosition == PacmanConstants.mainMenuCnt - 1) ? 0 : ++selectedMenuPosition;
	}

}
