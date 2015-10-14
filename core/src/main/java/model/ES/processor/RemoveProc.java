package model.ES.processor;

import util.LogUtil;

import com.simsilica.es.Entity;

import model.ES.component.ToRemove;
import controller.ECS.Processor;

public class RemoveProc extends Processor {

	@Override
	protected void registerSets() {
		register(ToRemove.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		entityData.removeEntity(e.getId());
	}

}
