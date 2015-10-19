package model.ES.processor.ability;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.assets.AbilityTrigger;

public class AbilityTriggerResetProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(AbilityTrigger.class);
	}
	
	
	@Override
	protected void onEntityEachTick(Entity e) {
		AbilityTrigger triggers = e.get(AbilityTrigger.class);
		triggers.triggers.clear();
	}
}
