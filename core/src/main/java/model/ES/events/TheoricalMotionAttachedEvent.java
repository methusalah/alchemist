package model.ES.events;

import util.entity.Entity;
import util.event.Event;

public class TheoricalMotionAttachedEvent extends Event {

	private final Entity e;
	
	public TheoricalMotionAttachedEvent(Entity e){
		this.e = e;
	}

	public Entity getEntity() {
		return e;
	}
}
