package model.ES.serial;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class EntityInstancier {

	private static EntityData entityData;

	public static void setEntityData(EntityData ed){
		entityData = ed;
	}
	
	public static void instanciate(EntityInstance instance){
		EntityId eid = instance.getBlueprint().createEntity(entityData, null);
		for(EntityComponent comp : instance.getComps())
			entityData.setComponent(eid, comp);
	}
}
