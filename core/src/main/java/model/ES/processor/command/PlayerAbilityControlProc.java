package model.ES.processor.command;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;
import model.ModelManager;
import model.ES.component.command.PlayerControl;
import model.ES.component.relation.AbilityLinks;
import model.ES.component.shipGear.Trigger;
import util.LogUtil;

public class PlayerAbilityControlProc extends Processor {

	@Override
	protected void registerSets() {
		register(AbilityLinks.class, PlayerControl.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
        for(EntitySet set : sets)
        	for (Entity e : set){
        		AbilityLinks links = e.get(AbilityLinks.class);
        		for(String abilityName : ModelManager.command.abilities){
        			EntityId ability = links.entities.get(abilityName);
        			if(ability != null)
        				entityData.setComponent(ability, new Trigger(e.getId(), false));
        		}
        	}
	}
}
