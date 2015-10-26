package model.ES.processor.ability;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.assets.Ability;
import model.ES.component.assets.TriggerRepeater;
import util.math.RandomUtil;

public class TriggerRepeaterProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Ability.class, TriggerRepeater.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Ability trigger = e.get(Ability.class);
		TriggerRepeater r = e.get(TriggerRepeater.class);
		
		if(trigger.isTriggered()){
			long start = System.currentTimeMillis();
			long nextPeriod = start + r.period + RandomUtil.between(0, r.periodRange);
			setComp(e, new TriggerRepeater(r.maxDuration, r.period, r.periodRange, start, nextPeriod));
		} else {
			if(r.start + r.maxDuration > System.currentTimeMillis()){
				if(r.nextPeriod < System.currentTimeMillis()){
					setComp(e, new Ability(trigger.getName(), true));
					long nextPeriod = System.currentTimeMillis() + r.period + RandomUtil.between(0, r.periodRange);
					setComp(e, new TriggerRepeater(r.maxDuration, r.period, r.periodRange, r.start, nextPeriod));
				}
			}
		}
	}

}
