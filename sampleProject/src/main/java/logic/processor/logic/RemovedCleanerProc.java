package logic.processor.logic;

import com.simsilica.es.Entity;

import component.lifeCycle.Removed;
import model.ECS.pipeline.Processor;

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
