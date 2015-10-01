package model.ES.processor.ability;

import model.ES.component.relation.AbilityTriggerList;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class AbilityTriggerResetProc extends Processor {

	@Override
	protected void registerSets() {
		register(AbilityTriggerList.class);
	}
	
	
	@Override
	protected void onUpdated(float elapsedTime) {
		for(Entity e : sets.get(0)){
			reset(e, elapsedTime);
		}
	}

	private void reset(Entity e, float elapsedTime) {
		AbilityTriggerList triggers = e.get(AbilityTriggerList.class);
		triggers.triggers.clear();
	}
}
