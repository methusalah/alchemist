package model.ECS.event;

import com.simsilica.es.EntityId;

import util.event.Event;

public class EntityAddedEvent extends Event {
	private final EntityId entityId;
	
	public EntityAddedEvent(EntityId entityId) {
		this.entityId = entityId;
	}

	public EntityId getEntityId() {
		return entityId;
	}
}
