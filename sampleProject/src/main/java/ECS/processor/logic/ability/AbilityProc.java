package ECS.processor.logic.ability;


import com.simsilica.es.Entity;

import ECS.commonLogic.Controlling;
import ECS.component.ability.Ability;
import ECS.component.ability.AbilityTrigger;
import ECS.component.ability.AbilityTriggerControl;
import model.ECS.pipeline.Processor;

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

			if(trigger.triggers.containsKey(a.getName()))
				setComp(e, new Ability(a.getName(), trigger.triggers.get(a.getName())));
			else
				setComp(e, new Ability(a.getName(), false));
				
		}
	}
}
