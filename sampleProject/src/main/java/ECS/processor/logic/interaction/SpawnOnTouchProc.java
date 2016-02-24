package ECS.processor.logic.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import ECS.component.lifeCycle.SpawnOnTouch;
import ECS.component.motion.PlanarStance;
import ECS.component.motion.Touching;
import model.ECS.blueprint.BlueprintLibrary;
import model.ECS.pipeline.Processor;

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
