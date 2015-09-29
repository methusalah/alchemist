package model.ES.processor.shipGear;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.shipGear.Trigger;
import util.LogUtil;

public class ResetTriggerProc extends Processor {

	@Override
	protected void registerSets() {
		register(Trigger.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}

	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime) {
		Trigger t = e.get(Trigger.class);
		if(!t.isToggle)
			entityData.removeComponent(e.getId(), Trigger.class);
	}
}
