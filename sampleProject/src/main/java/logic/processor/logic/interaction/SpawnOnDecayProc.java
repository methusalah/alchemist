package logic.processor.logic.interaction;

import com.brainless.alchemist.model.ECS.blueprint.BlueprintLibrary;
import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import component.lifeCycle.SpawnOnDecay;
import component.lifeCycle.ToRemove;
import component.motion.PlanarStance;

/**
 * Must be called before ToRemove processor
 * @author Benoît
 *
 */
public class SpawnOnDecayProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(SpawnOnDecay.class, ToRemove.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		SpawnOnDecay spawn = e.get(SpawnOnDecay.class);

		for(String bpName : spawn.getBlueprintNames()) {
			EntityId spawned = BlueprintLibrary.getBlueprint(bpName).createEntity(entityData, null);
			PlanarStance stance = entityData.getComponent(e.getId(), PlanarStance.class);
			if(stance != null && entityData.getComponent(spawned, PlanarStance.class) != null)
				entityData.setComponent(spawned, stance);
		}
	}
}
