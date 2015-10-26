package model.ES.processor.ability;


import model.ES.component.assets.AbilityTrigger;
import model.ES.commonLogic.Controlling;
import model.ES.component.assets.Ability;
import model.ES.component.hierarchy.AbilityTriggerControl;
import model.ES.component.hierarchy.Parenting;

import com.simsilica.es.Entity;
import controller.ECS.Processor;

public class AbilityProc extends Processor {

	@Override
	protected void registerSets() {
		register("triggered", Ability.class, AbilityTriggerControl.class);
	}

	
	@Override
	protected void onEntityEachTick(Entity e) {
		AbilityTriggerControl control = e.get(AbilityTriggerControl.class);
		
		if(control.isActive()){
			AbilityTrigger trigger = Controlling.getControl(AbilityTrigger.class, e.getId(), entityData);
			if(trigger == null)
				return;
	
			Ability a = e.get(Ability.class);
				
			if(trigger.triggers.containsKey(a.getName()) && trigger.triggers.get(a.getName()))
				setComp(e, new Ability(a.getName(), trigger.triggers.get(a.getName())));
		}
	}
}
