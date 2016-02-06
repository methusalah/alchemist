package model.ECS;

import java.util.HashMap;
import java.util.Map;

import com.simsilica.es.EntityChange;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityComponentListener;
import com.simsilica.es.EntityId;

import util.LogUtil;

public class EntityDataObserver implements EntityComponentListener {
	public Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> entities = new HashMap<>();

	@Override
	public void componentChange(EntityChange change) {
		if(!entities.containsKey(change.getEntityId()))
			entities.put(change.getEntityId(), new HashMap<>());
		
		Map<Class<? extends EntityComponent>, EntityComponent> components = entities.get(change.getEntityId());
		if(change.getComponent() == null)
			components.remove(change.getComponentType());
		else
			components.put(change.getComponentType(), change.getComponent());
	}
}

