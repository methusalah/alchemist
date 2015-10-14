package model.ES.processor.ability;

import model.ES.component.Cooldown;
import model.ES.component.shipGear.Trigger;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class TriggerCancelationProc extends Processor {

	@Override
	protected void registerSets() {
		register(Cooldown.class, Trigger.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Cooldown cd = e.get(Cooldown.class);
		Trigger t = e.get(Trigger.class);
		if(t.triggered && cd.start + cd.duration > System.currentTimeMillis())
			setComp(e, new Trigger(t.name, false));
	}
}