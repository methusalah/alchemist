package model.ES.processor;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.lifeCycle.Removed;
import model.ES.component.lifeCycle.ToRemove;

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
