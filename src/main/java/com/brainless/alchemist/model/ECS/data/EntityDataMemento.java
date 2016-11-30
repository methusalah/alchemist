package com.brainless.alchemist.model.ECS.data;

import java.util.Map;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 * Memento of an entity data, used to restore a previously saved state.
 * 
 * @author benoit
 *
 */
public class EntityDataMemento {
	private final SavableEntityData originator;
	private final Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> state;
	
	/**
	 * Build a new immutable entity data memento, from the given savableEntityData originator.
	 * @param state
	 * @param originator
	 */
	public EntityDataMemento(Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> state, SavableEntityData originator) {
		this.state = state;
		this.originator = originator;
	}

	public Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> getState() {
		return state;
	}

	/**
	 * The memento originator. A memento can only be applied on its original source. 
	 * @return
	 */
	public SavableEntityData getOriginator() {
		return originator;
	}
}
