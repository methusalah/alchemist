package model.ES.processor.command;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.ECS.Processor;
import model.ModelManager;
import model.ES.component.assets.AbilityTrigger;
import model.ES.component.assets.Ability;
import model.ES.component.command.PlayerControl;
import util.LogUtil;

public class PlayerAbilityControlProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(AbilityTrigger.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		AbilityTrigger triggers = e.get(AbilityTrigger.class);
		triggers.triggers.clear();
		for(String abilityName : ModelManager.command.abilities){
			triggers.triggers.put(abilityName, true);
		}
	}
}
