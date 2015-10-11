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
	
	private void addEntity(EntityId eid){
		Naming naming = entityData.getComponent(eid, Naming.class);
		Parenting parenting = entityData.getComponent(eid, Parenting.class);
		if(parenting == null){
			EntityNode n = new EntityNode(eid, naming.name);
			baseNodes.add(n);
			allNodes.put(eid, n);
		} else
			allNodes.get(parenting.parent).children.add(new EntityNode(eid, naming.name));
	}

}
