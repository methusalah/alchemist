package model.ES.processor.ability;


import com.simsilica.es.Entity;

import model.ES.component.relation.AbilityTriggerList;
import model.ES.component.relation.Parenting;
import model.ES.component.shipGear.Trigger;
import controller.ECS.Processor;

public class TriggerObserverProc extends Processor {

	@Override
	protected void registerSets() {
		register(Trigger.class, Parenting.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Trigger t = e.get(Trigger.class);
		Parenting p = e.get(Parenting.class);
		AbilityTriggerList parentTriggers = entityData.getComponent(p.getParent(), AbilityTriggerList.class);
		if(parentTriggers.triggers.containsKey(t.name) && parentTriggers.triggers.get(t.name))
			setComp(e, new Trigger(t.name, true));
		else
			setComp(e, new Trigger(t.name, false));
	}
}
