package model.ES.serial;

import java.util.ArrayList;
import java.util.List;

import model.ES.component.relation.Parenting;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class EntityBlueprint {
	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
	public List<EntityComponent> comps = new ArrayList<>();
	public List<EntityBlueprint> children = new ArrayList<>();
	
	public EntityId create(EntityData entityData, EntityId parent){
		EntityId res = entityData.createEntity();
		for(EntityComponent comp : comps)
			entityData.setComponent(res, comp);
		for(EntityBlueprint childBP : children){
			EntityId child = childBP.create(entityData, res);
			entityData.setComponent(child, new Parenting(res));
		}
		return res;
	}

}
