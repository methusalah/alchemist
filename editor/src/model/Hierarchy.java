package model;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.ES.serial.Blueprint;
import util.event.EventManager;

public class Hierarchy {
	private final EntityData entityData;

	public Hierarchy(EntityData entityData) {
		this.entityData = entityData;
		EventManager.register(this);
	}
	
	public void createNewEntity(){
		EntityId eid = entityData.createEntity();
		entityData.setComponent(eid, new Naming("Unamed entity"));
	}

	public void createNewEntity(Blueprint bp){
		bp.createEntity(entityData, null);
	}
	
	public void removeEntity(EntityPresenter ep){
		for(EntityPresenter childNode : ep.childrenListProperty()){
			entityData.removeEntity(childNode.getEntityId());
		}
		entityData.removeEntity(ep.getEntityId());
	}
	
	public void updateParenting(EntityPresenter child, EntityPresenter parent){
		entityData.setComponent(child.getEntityId(), new Parenting(parent.getEntityId()));
	}
}
