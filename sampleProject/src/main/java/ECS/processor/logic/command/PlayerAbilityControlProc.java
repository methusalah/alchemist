package ECS.processor.logic.command;


import com.simsilica.es.Entity;

import ECS.component.ability.AbilityTrigger;
import ECS.component.ability.PlayerControl;
import command.CommandPlatform;
import model.ECS.pipeline.Processor;

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
