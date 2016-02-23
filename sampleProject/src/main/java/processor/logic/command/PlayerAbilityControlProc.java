package processor.logic.command;


import com.simsilica.es.Entity;

import component.ability.AbilityTrigger;
import component.ability.PlayerControl;
import model.CommandPlatform;
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
