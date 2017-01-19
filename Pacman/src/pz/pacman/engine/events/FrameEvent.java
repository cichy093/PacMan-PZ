package pz.pacman.engine.events;

import pz.pacman.constants.FrameEventType;

public class FrameEvent {
	
	private FrameEventType type;
	
	public FrameEvent(FrameEventType type) {
		this.type = type;
	}
	
	public FrameEventType getType() {
		return this.type;
	}
}
