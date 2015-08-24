package model.ES.events;

import util.entity.Entity;
import util.event.Event;

public class PossibleMotionAttachedEvent extends Event {

	private final Entity e;
	
	public PossibleMotionAttachedEvent(Entity e){
		this.e = e;
	}

	public Entity getEntity() {
		return e;
	}
}
