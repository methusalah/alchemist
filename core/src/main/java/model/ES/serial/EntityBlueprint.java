package model.ES.serial;

import java.util.ArrayList;
import java.util.List;

import model.ES.component.relation.Parenting;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class EntityBlueprint {
	public List<EntityComponent> comps = new ArrayList<>();
	public List<EntityBlueprint> children = new ArrayList<>();
	
	public EntityId create(EntityData entityData, EntityId parent){
		EntityId res = entityData.createEntity();
		for(EntityComponent comp : comps)
			if(comp instanceof Parenting)
				entityData.setComponent(res, new Parenting(parent));
			else
				entityData.setComponent(res, comp);
		for(EntityBlueprint child : children)
			child.create(entityData, res);
		return res;
	}

}
