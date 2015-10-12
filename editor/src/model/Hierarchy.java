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
import model.ES.component.relation.Parenting;

public class Hierarchy {
	private final EntityData entityData;
	public final List<EntityNode> baseNodes = new ArrayList<>();
	private final Map<EntityId, EntityNode> allNodes = new HashMap<>();

	public Hierarchy(EntityData entityData) {
		this.entityData = entityData;
		
		EntitySet set = entityData.getEntities(Naming.class);
		for(Entity e : set){
			addEntity(e.getId());
		}
	}
	
	public void createNewEntity(String Name){
		EntityId newEntityId = entityData.createEntity();
		entityData.setComponent(newEntityId, new Naming(Name));
		addEntity(newEntityId);
	}
	
	private void addEntity(EntityId eid){
		Naming naming = entityData.getComponent(eid, Naming.class);
		EntityNode n = new EntityNode(eid, naming.name);
		allNodes.put(eid, n);

		Parenting parenting = entityData.getComponent(eid, Parenting.class);
		if(parenting == null){
			baseNodes.add(n);
		} else {
			allNodes.get(parenting.getParent()).children.add(new EntityNode(eid, naming.name));
		}
	}
	
	public void updateName(EntityId eid){
		Naming naming = entityData.getComponent(eid, Naming.class);
		allNodes.get(eid).setName(naming.name);
	}
}
