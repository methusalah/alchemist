package model.ES.processor;

import model.ES.component.Removed;
import model.ES.component.ToRemove;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class RemoveProc extends Processor {

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
