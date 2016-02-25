package logic.processor.logic;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;

import component.lifeCycle.Removed;
import component.lifeCycle.ToRemove;

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
