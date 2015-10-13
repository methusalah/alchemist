package model.ES.serial;

import util.LogUtil;
import model.ES.component.Naming;
import model.ES.component.relation.Parenting;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class BlueprintCreator {
	private static EntityData entityData;

	public static void setEntityData(EntityData ed){
		entityData = ed;
	}
	
	public static EntityId create(String bluePrintName, EntityId parent){
		Blueprint bp = BlueprintLibrary.get(bluePrintName);
		assert bp != null : bluePrintName + " blueprint can't be found.";
		
		EntityId res = entityData.createEntity();
		
		for(EntityComponent comp : bp.getComps()){
			entityData.setComponent(res, comp);
		}
		for(String childBPName : bp.getChildren()){
			EntityId child = create(childBPName, res);
			entityData.setComponent(child, new Parenting(res));
		}
		Naming n = entityData.getComponent(res, Naming.class);
		if(n != null)
			LogUtil.info("creation of "+bluePrintName+" (#"+res.getId()+") named '"+n.name+"' with parent #"+parent);
		else
			LogUtil.info("creation of "+bluePrintName+" (#"+res.getId()+") without naming with parent #"+parent);
		return res;
	}
}
