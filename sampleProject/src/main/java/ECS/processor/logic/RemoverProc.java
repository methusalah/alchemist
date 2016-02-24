package ECS.processor.logic;

import com.simsilica.es.Entity;

import ECS.component.lifeCycle.Removed;
import ECS.component.lifeCycle.ToRemove;
import model.ECS.pipeline.Processor;

public class RemoverProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(ToRemove.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		entityData.removeEntity(e.getId());
		setComp(e, new Removed());
	}

}
