package logic.processor.logic.command;


import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;

import command.CommandPlatform;
import component.ability.AbilityTrigger;
import component.ability.PlayerControl;

public class PlayerAbilityControlProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(AbilityTrigger.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		AbilityTrigger triggers = e.get(AbilityTrigger.class);
		triggers.triggers.clear();
		for(String abilityName : CommandPlatform.abilities){
			triggers.triggers.put(abilityName, true);
		}
	}
}
