package logic.processor.logic.ability;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;

import component.ability.AbilityTrigger;

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
