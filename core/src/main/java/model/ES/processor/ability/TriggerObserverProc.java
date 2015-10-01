package model.ES.processor.ability;


import com.simsilica.es.Entity;

import model.ES.component.relation.AbilityTriggerList;
import model.ES.component.shipGear.Trigger;
import controller.entityAppState.Processor;

public class TriggerObserverProc extends Processor {

	@Override
	protected void registerSets() {
		register(Trigger.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
		for(Entity e : sets.get(0)){
			Trigger t = e.get(Trigger.class);
			AbilityTriggerList parentTriggers = entityData.getComponent(t.source, AbilityTriggerList.class);
			if(parentTriggers.triggers.containsKey(t.name) && parentTriggers.triggers.get(t.name))
				setComp(e, new Trigger(t.source, t.name, true));
			else
				setComp(e, new Trigger(t.source, t.name, false));
		}
	}
}
