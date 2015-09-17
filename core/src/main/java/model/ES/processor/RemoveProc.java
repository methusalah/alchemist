package model.ES.processor;

import com.simsilica.es.Entity;

import model.ES.component.ToRemove;
import controller.entityAppState.Processor;

public class RemoveProc extends Processor {

	@Override
	protected void registerSets() {
		register(ToRemove.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		entityData.removeEntity(e.getId());
	}

}
