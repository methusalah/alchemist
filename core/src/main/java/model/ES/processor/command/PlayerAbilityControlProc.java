package model.ES.processor.command;


import model.ES.component.ability.AbilityTrigger;
import model.ES.component.ability.PlayerControl;
import model.tempImport.Command;
import model.tempImport.DataState;

import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;

import main.java.model.ECS.pipeline.Processor;

public class PlayerAbilityControlProc extends Processor {

	private Command command; 
	
	@Override
	protected void onInitialized(AppStateManager stateManager) {
		command = stateManager.getState(DataState.class).getCommand();
	}
	
	@Override
	protected void registerSets() {
		registerDefault(AbilityTrigger.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		AbilityTrigger triggers = e.get(AbilityTrigger.class);
		triggers.triggers.clear();
		for(String abilityName : command.abilities){
			triggers.triggers.put(abilityName, true);
		}
	}
}
