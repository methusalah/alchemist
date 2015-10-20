package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import util.LogUtil;

public class Hierarchy {
	private final EntityData entityData;
	public final List<EntityPresenter> baseNodes = new ArrayList<>();
	private final Map<EntityId, EntityPresenter> allNodes = new HashMap<>();

	public Hierarchy(EntityData entityData) {
		this.entityData = entityData;
		createEntityHierarchy();
	}
	
	public void createNewEntity(String Name){
		EntityId newEntityId = entityData.createEntity();
		entityData.setComponent(newEntityId, new Naming(Name));
		createEntityHierarchy();
	}

	public void removeEntity(EntityId eid){
		entityData.removeEntity(eid);
		for(EntityPresenter childNode : allNodes.get(eid).childrenListProperty()){
			entityData.removeEntity(childNode.getEntityId());
		}
		createEntityHierarchy();
	}
	
	private void addEntity(EntityId eid){
		Naming naming = entityData.getComponent(eid, Naming.class);
		EntityPresenter n = new EntityPresenter(eid, naming.name);
		allNodes.put(eid, n);
	}
	
	private void linkEntity(EntityId eid){
		EntityPresenter n = allNodes.get(eid);

		Parenting parenting = entityData.getComponent(eid, Parenting.class);
		if(parenting == null){
			baseNodes.add(n);
		} else {
			allNodes.get(parenting.getParent()).childrenListProperty().add(n);
		}
	}
	
	public void updateName(EntityId eid){
		Naming naming = entityData.getComponent(eid, Naming.class);
		allNodes.get(eid).nameProperty().setValue(naming.name);
	}
	
	public void updateParenting(EntityId eid, EntityId parentid){
		entityData.setComponent(eid, new Parenting(parentid));
		LogUtil.info("update parenting of "+entityData.getComponent(eid, Naming.class).name +" to "+entityData.getComponent(parentid, Naming.class).name );
		createEntityHierarchy();
	}
	
	public void createEntityHierarchy(){
		allNodes.clear();
		baseNodes.clear();
		EntitySet set = entityData.getEntities(Naming.class);
		for(Entity e : set){
			addEntity(e.getId());
		}
		for(Entity e : set){
			linkEntity(e.getId());
		}
	}
}
