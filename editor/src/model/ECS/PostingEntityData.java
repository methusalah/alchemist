package model.ECS;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;

import model.ECS.event.ComponentSetEvent;
import model.ECS.event.EntityAddedEvent;
import model.ECS.event.EntityRemovedEvent;
import util.LogUtil;
import util.event.EventManager;

public class PostingEntityData extends DefaultEntityData{
	
	@Override
	public void setComponent(EntityId entityId, EntityComponent component) {
		EntityComponent lastComp = getComponent(entityId, component.getClass());
		super.setComponent(entityId, component);
		EventManager.post(new ComponentSetEvent(entityId, component.getClass(), lastComp, component));
	}
	
	@Override
	public boolean removeComponent(EntityId entityId, Class type) {
		EntityComponent lastComp = getComponent(entityId, type);
		EventManager.post(new ComponentSetEvent(entityId, type, lastComp, null));
		return super.removeComponent(entityId, type);
	}

	@Override
	public EntityId createEntity() {
		EntityId res = super.createEntity();
		EventManager.post(new EntityAddedEvent(res));
		return res;
	}
	
	@Override
	public void removeEntity(EntityId arg0) {
		super.removeEntity(arg0);
		EventManager.post(new EntityRemovedEvent(arg0));
	}
}
