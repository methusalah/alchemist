package logic.processor.logic.interaction;

import com.brainless.alchemist.model.ECS.blueprint.BlueprintLibrary;
import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import component.lifeCycle.SpawnMultipleOnBorn;
import component.motion.PlanarStance;
import util.math.RandomUtil;

public class SpawnMultipleOnBornProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(SpawnMultipleOnBorn.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		SpawnMultipleOnBorn s = e.get(SpawnMultipleOnBorn.class);
		
		int count = RandomUtil.between(s.getCount(), s.getRange());
		
		for(int i = 0; i < count; i++){
			EntityId spawned = BlueprintLibrary.getBlueprint(s.getBlueprintName()).createEntity(entityData, null);
			PlanarStance stance = entityData.getComponent(e.getId(), PlanarStance.class);
			if(stance != null && entityData.getComponent(spawned, PlanarStance.class) != null)
				entityData.setComponent(spawned, stance);
		}
	}
}
