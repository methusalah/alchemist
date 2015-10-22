package model.ECS.event;

import com.simsilica.es.EntityId;

import util.event.Event;

public class EntityRemovedEvent extends Event {
	private final EntityId entityId;
	
	public EntityRemovedEvent(EntityId entityId) {
		this.entityId = entityId;
	}

	public EntityId getEntityId() {
		return entityId;
	}
}
