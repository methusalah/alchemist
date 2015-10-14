package model.ES.processor.command;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.ECS.Processor;
import model.ModelManager;
import model.ES.component.command.PlayerControl;
import model.ES.component.relation.AbilityTriggerList;
import model.ES.component.shipGear.Trigger;
import util.LogUtil;

public class PlayerAbilityControlProc extends Processor {

	@Override
	protected void registerSets() {
		register(AbilityTriggerList.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		AbilityTriggerList triggers = e.get(AbilityTriggerList.class);
		triggers.triggers.clear();
		for(String abilityName : ModelManager.command.abilities){
			triggers.triggers.put(abilityName, true);
		}
	}
}
