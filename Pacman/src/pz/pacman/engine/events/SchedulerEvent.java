package pz.pacman.engine.events;

import pz.pacman.constants.SchedulerEventType;


public class SchedulerEvent {
	
	private SchedulerEventType type;
	
	public SchedulerEvent(SchedulerEventType type) {
		this.type = type;
	}
	
	public SchedulerEventType getType() {
		return this.type;
	}
}