package pz.pacman.mvc.model;

import pz.pacman.engine.events.EngineEvent;
import pz.pacman.engine.events.LoadSaveEvent;
import pz.pacman.engine.singleton.Engine;

public interface BaseModel {
	
	public default void notifyEngine(EngineEvent e) {
		Engine.getInstance().OnEvent(e);
	}
	
	public default void notifyEngine(LoadSaveEvent e) {
		Engine.getInstance().OnLoadSaveEvent(e);
	}
}
