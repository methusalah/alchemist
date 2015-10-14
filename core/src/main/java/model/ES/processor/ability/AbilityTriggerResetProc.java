package model.ES.processor.ability;

import model.ES.component.relation.AbilityTriggerList;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class AbilityTriggerResetProc extends Processor {

	@Override
	protected void registerSets() {
		register(AbilityTriggerList.class);
	}
	
	
	@Override
	protected void onEntityEachTick(Entity e) {
		AbilityTriggerList triggers = e.get(AbilityTriggerList.class);
		triggers.triggers.clear();
	}
}
