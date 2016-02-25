package logic.commonLogic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import component.motion.PlanarStance;
import model.ECS.blueprint.Blueprint;
import model.ECS.blueprint.BlueprintLibrary;
import util.geometry.geom2d.Point2D;

public class Spawner {

	public static List<EntityId> spawnWithSpawnerStance(EntityData entityData, EntityId spawner, List<String> blueprintNames){
		List<EntityId> res = new ArrayList<>();
		for(String bpName : blueprintNames)
			res.add(spawnWithSpawnerStance(entityData, spawner, bpName));
		return res;
	}

	public static EntityId spawnWithSpawnerStance(EntityData entityData, EntityId spawner, String blueprintName){
		Blueprint bp = getBlueprint(blueprintName);
		if(bp != null){
			EntityId res = bp.createEntity(entityData, null);
			PlanarStance stance = entityData.getComponent(spawner, PlanarStance.class);
			if(stance != null && entityData.getComponent(res, PlanarStance.class) != null)
				entityData.setComponent(res, stance);
			return res;
		} else
			return null;
	}
	
	public static List<EntityId> spawnAtCoord(EntityData entityData, List<Point2D> coords, List<String> blueprintNames){
		List<EntityId> res = new ArrayList<>();
		int i = 0;
		for(String bpName : blueprintNames)
			res.add(spawnAtCoord(entityData, coords.get(i++), bpName));
		return res;
	}

	public static EntityId spawnAtCoord(EntityData entityData, Point2D coord, String blueprintName){
		Blueprint bp = getBlueprint(blueprintName);
		if(bp != null){
			EntityId res = bp.createEntity(entityData, null);
			PlanarStance stance = entityData.getComponent(res, PlanarStance.class);  
			if(stance != null)
				entityData.setComponent(res, new PlanarStance(coord, stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
			return res;
		} else
			return null;
	}
	
	private static Blueprint getBlueprint(String blueprintName){
		Blueprint bp = BlueprintLibrary.getBlueprint(blueprintName);
		if(bp == null)
			LoggerFactory.getLogger(Spawner.class).warn("Blueprint can't be found (" + blueprintName + ").");
		return bp;
	}

}
