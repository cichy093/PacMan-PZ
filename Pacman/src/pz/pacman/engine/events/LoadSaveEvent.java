package pz.pacman.engine.events;

import pz.pacman.constants.LoadSaveMode;

public class LoadSaveEvent {

	private LoadSaveMode mode;
	private String path;
	
	public LoadSaveEvent(LoadSaveMode mode, String path) {
		this.mode = mode;
		this.path = path;
	}

	public LoadSaveMode getMode() {
		return this.mode;
	}

	public String getPath() {
		return path;
	}
}
