package model;

import java.util.HashMap;
import java.util.Map;

import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.ES.serial.PrototypeCreator;
import util.LogUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

public class Hierarchy {
	private final EntityData entityData;
	private final EntityPresenter rootEntityPresenter;
	private final Map<EntityId, EntityPresenter> presenters = new HashMap<>();

	public Hierarchy(EntityData entityData) {
		this.entityData = entityData;
		rootEntityPresenter = new EntityPresenter(null, "root");
		createEntityHierarchy();
	}
	
	public EntityPresenter getRootEntityPresenter() {
		return rootEntityPresenter;
	}

	public void createNewEntity(){
		String name = "Unamed entity";
		// update entityData
		EntityId newEntityId = entityData.createEntity();
		entityData.setComponent(newEntityId, new Naming(name));

		//update presenters
		EntityPresenter ep = new EntityPresenter(newEntityId, name);
		rootEntityPresenter.childrenListProperty().add(ep);
		addEntityPresenter(ep);
	}

	public void createNewEntityFromBlueprint(Blueprint bp){
		EntityPresenter ep = bp.getEntityPresenter(entityData, null);
		rootEntityPresenter.childrenListProperty().add(ep);
		addEntityPresenter(ep);
	}
	
	private void addEntityPresenter(EntityPresenter ep){
		presenters.put(ep.getEntityId(), ep);
		for(EntityPresenter child : ep.childrenListProperty())
			addEntityPresenter(child);
	}
	
	public void removeEntity(EntityPresenter ep){
		// update presenters		
		removePresenterFromParent(ep);
		presenters.remove(ep.getEntityId());
		
		// update entityData
		for(EntityPresenter childNode : ep.childrenListProperty()){
			entityData.removeEntity(childNode.getEntityId());
		}
		entityData.removeEntity(ep.getEntityId());
	}
	
	public void updateParenting(EntityPresenter child, EntityPresenter parent){
		// update presenters
		removePresenterFromParent(child);
		parent.childrenListProperty().add(child);

		// update entityData
		entityData.setComponent(child.getEntityId(), new Parenting(parent.getEntityId()));
	}
	
	public EntityPresenter getPresenter(EntityId eid){
		return presenters.get(eid);
	}
	
	private void removePresenterFromParent(EntityPresenter ep){
		if(ep != rootEntityPresenter){
			Parenting parenting = entityData.getComponent(ep.getEntityId(), Parenting.class);
			if(parenting != null){
				EntityPresenter parentPresenter = getPresenter(parenting.getParent());
				LogUtil.info("removing from parent "+parentPresenter);
				if(parentPresenter != null)
					parentPresenter.childrenListProperty().remove(ep);
			} else
				rootEntityPresenter.childrenListProperty().remove(ep);
		}
	}
	
	private void createEntityHierarchy(){
		presenters.clear();
		EntitySet set = entityData.getEntities(Naming.class);
		for(Entity e : set){
			addEntity(e.getId());
		}
		for(Entity e : set){
			linkEntity(e.getId());
		}
	}
	
	private void addEntity(EntityId eid){
		Naming naming = entityData.getComponent(eid, Naming.class);
		EntityPresenter n = new EntityPresenter(eid, naming.name);
		presenters.put(eid, n);
	}
	
	private void linkEntity(EntityId eid){
		EntityPresenter n = presenters.get(eid);

		Parenting parenting = entityData.getComponent(eid, Parenting.class);
		EntityPresenter parent = parenting == null? rootEntityPresenter : presenters.get(parenting.getParent());
		parent.childrenListProperty().add(n);
	}
}
