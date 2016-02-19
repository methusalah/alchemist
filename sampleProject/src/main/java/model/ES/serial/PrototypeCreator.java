package model.ES.serial;

import util.LogUtil;
import model.ECS.Naming;
import model.ECS.Parenting;
import model.ES.component.lifeCycle.LifeTime;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class PrototypeCreator {
	private static EntityData entityData;

	public static void setEntityData(EntityData ed){
		entityData = ed;
	}
	
	public static EntityId create(String bluePrintName, EntityId parent){
		EntityPrototype bp = PrototypeLibrary.get(bluePrintName);
		if(bp == null)
			LogUtil.info(bluePrintName + " blueprint can't be found.");
		
		EntityId res = entityData.createEntity();
		
		for(EntityComponent comp : bp.getComps()){
			entityData.setComponent(res, comp);
		}
		for(String childBPName : bp.getChildren()){
			EntityId child = create(childBPName, res);
			entityData.setComponent(child, new Parenting(res));
		}
		return res;
	}
}
