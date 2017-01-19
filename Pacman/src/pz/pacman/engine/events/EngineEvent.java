package pz.pacman.engine.events;

import pz.pacman.constants.EngineEventType;

public class EngineEvent {
	
	private EngineEventType type;
	
	public EngineEvent(EngineEventType type) {
		this.type = type;
	}
	
	public EngineEventType getType() {
		return this.type;
	}
}