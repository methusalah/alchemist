package processor.logic.ability;

import com.simsilica.es.Entity;

import model.ECS.pipeline.Processor;

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
