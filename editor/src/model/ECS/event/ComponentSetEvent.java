package model.ECS.event;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.event.Event;

public class ComponentSetEvent extends Event {
	private final EntityId entityId;
	private final Class<? extends EntityComponent> compClass;
	private final EntityComponent lastComp, newComp;
	
	public ComponentSetEvent(EntityId entityId, Class<? extends EntityComponent> compClass, EntityComponent lastComp, EntityComponent newComp) {
		this.entityId = entityId;
		this.compClass = compClass;
		this.lastComp = lastComp;
		this.newComp = newComp;
	}

	public EntityId getEntityId() {
		return entityId;
	}

	public EntityComponent getLastComp() {
		return lastComp;
	}

	public EntityComponent getNewComp() {
		return newComp;
	}

	public Class<? extends EntityComponent> getCompClass() {
		return compClass;
	}
	
}
