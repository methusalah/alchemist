package logic.processor.logic.ability;


import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.ability.Ability;
import component.ability.AbilityTrigger;
import component.ability.AbilityTriggerControl;
import logic.commonLogic.Controlling;

public class AbilityProc extends BaseProcessor {

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
