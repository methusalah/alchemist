package model.ES.processor.ability;


import model.ES.component.relation.AbilityTriggerList;
import model.ES.component.relation.Parenting;
import model.ES.component.shipGear.Trigger;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class TriggerObserverProc extends Processor {

	@Override
	protected void registerSets() {
		register(Trigger.class, Parenting.class);
		register(AbilityTriggerList.class);
	}

	@Override
	protected void onUpdated() {
		for(Entity child : sets.get(0)){
			Entity parent = sets.get(1).getEntity(child.get(Parenting.class).getParent());
			if(parent != null){
				Trigger t = child.get(Trigger.class);
				AbilityTriggerList parentTriggers = parent.get(AbilityTriggerList.class);
				
				if(parentTriggers.triggers.containsKey(t.name) && parentTriggers.triggers.get(t.name))
					setComp(child, new Trigger(t.name, true));
				else
					setComp(child, new Trigger(t.name, false));
			}
		}
	}
}
