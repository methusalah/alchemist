package model.ES.processor.ability;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.shipGear.Trigger;
import model.ES.component.shipGear.TriggerRepeater;
import util.math.RandomUtil;

public class TriggerRepeaterProc extends Processor {

	@Override
	protected void registerSets() {
		register(Trigger.class, TriggerRepeater.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}

	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime) {
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
