package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.ES.serial.Blueprint;
import util.event.EventManager;

public class HierarchyPresenter {
	private final EntityData entityData;
	public final ObjectProperty<EntityPresenter> selectionProperty = new SimpleObjectProperty<>();

	public HierarchyPresenter(EntityData entityData) {
		this.entityData = entityData;
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
