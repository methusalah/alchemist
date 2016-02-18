package main.java.model.ECS.data;

import java.util.Map;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class EntityDataMemento {
	private final SavableEntityData originator;
	private final Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> state;
	
	public EntityDataMemento(Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> state, SavableEntityData originator) {
		this.state = state;
		this.originator = originator;
	}

	public Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> getState() {
		return state;
	}

	public SavableEntityData getOriginator() {
		return originator;
	}
}
