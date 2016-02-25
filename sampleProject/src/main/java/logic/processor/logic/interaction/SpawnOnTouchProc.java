package logic.processor.logic.interaction;

import com.brainless.alchemist.model.ECS.blueprint.BlueprintLibrary;
import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import component.lifeCycle.SpawnOnTouch;
import component.motion.PlanarStance;
import component.motion.Touching;

public class SpawnOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(SpawnOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		SpawnOnTouch spawn = e.get(SpawnOnTouch.class);
		Touching touching = e.get(Touching.class);
		
		for(String bpName : spawn.getBlueprintNames()) {
			EntityId spawned = BlueprintLibrary.getBlueprint(bpName).createEntity(entityData, null);
			PlanarStance stance = entityData.getComponent(spawned, PlanarStance.class); 
			if(stance != null)
				entityData.setComponent(spawned, new PlanarStance(touching.getCoord(), stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
		}
	}
}
