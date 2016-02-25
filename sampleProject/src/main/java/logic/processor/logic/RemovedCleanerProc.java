package logic.processor.logic;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;

import component.lifeCycle.Removed;

public class RemovedCleanerProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Removed.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		entityData.removeEntity(e.getId());
	}

}
