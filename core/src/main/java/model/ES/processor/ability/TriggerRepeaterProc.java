package model.ES.processor.ability;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.shipGear.Trigger;
import model.ES.component.shipGear.TriggerRepeater;
import util.math.RandomUtil;

public class TriggerRepeaterProc extends Processor {

	@Override
	protected void registerSets() {
		register(Trigger.class, TriggerRepeater.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Trigger trigger = e.get(Trigger.class);
		TriggerRepeater r = e.get(TriggerRepeater.class);
		
		if(trigger.triggered){
			long start = System.currentTimeMillis();
			long nextPeriod = start + r.period + RandomUtil.between(0, r.periodRange);
			setComp(e, new TriggerRepeater(r.maxDuration, r.period, r.periodRange, start, nextPeriod));
		} else {
			if(r.start + r.maxDuration > System.currentTimeMillis()){
				if(r.nextPeriod < System.currentTimeMillis()){
					setComp(e, new Trigger(trigger.name, true));
					long nextPeriod = System.currentTimeMillis() + r.period + RandomUtil.between(0, r.periodRange);
					setComp(e, new TriggerRepeater(r.maxDuration, r.period, r.periodRange, r.start, nextPeriod));
				}
			}
		}
	}

}
