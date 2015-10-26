package model.ES.processor.ability;

import model.ES.component.Cooldown;
import model.ES.component.assets.Ability;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class TriggerCancelationProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Cooldown.class, Ability.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Cooldown cd = e.get(Cooldown.class);
		Ability t = e.get(Ability.class);
		if(t.isTriggered() && cd.start + cd.duration > System.currentTimeMillis())
			setComp(e, new Ability(t.getName(), false));
	}
}