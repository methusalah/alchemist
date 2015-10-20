package model.ES.processor.ability;


import model.ES.component.assets.AbilityTrigger;
import model.ES.commonLogic.Controlling;
import model.ES.component.assets.Ability;
import model.ES.component.hierarchy.AbilityTriggerControl;
import model.ES.component.hierarchy.Parenting;

import com.simsilica.es.Entity;
import controller.ECS.Processor;

public class TriggerObserverProc extends Processor {

	@Override
	protected void registerSets() {
		register("triggered", Ability.class, AbilityTriggerControl.class, Parenting.class);
	}

	
	@Override
	protected void onEntityEachTick(Entity e) {
		Ability a = e.get(Ability.class);
		AbilityTrigger trigger = Controlling.getControl(AbilityTrigger.class, e.getId(), entityData);
		
		if(trigger == null)
			return;
			
		if(trigger.triggers.containsKey(a.name) && trigger.triggers.get(a.name))
			setComp(e, new Ability(a.name, true));
		else
			setComp(e, new Ability(a.name, false));
	}
}
