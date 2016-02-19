package test.java.processor.logic.ability;

import com.simsilica.es.Entity;

import main.java.model.ECS.pipeline.Processor;
import model.ES.component.ability.Ability;
import model.ES.component.ability.TriggerRepeater;
import model.tempImport.LogicLoop;
import util.math.RandomUtil;

public class TriggerRepeaterProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Ability.class, TriggerRepeater.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Ability ability = e.get(Ability.class);
		TriggerRepeater r = e.get(TriggerRepeater.class);
		
		if(ability.isTriggered()){
			// reset of the repeater to trigger again in "period+period range" millisecond
			setComp(e, new TriggerRepeater(r.getMaxDuration(),
					r.getPeriod(),
					r.getPeriodRange(),
					r.getMaxDuration(),
					r.getPeriod() + RandomUtil.between(0, r.getPeriodRange())));
		} else {
			if(r.getRemainingBeforePeriod() < 0 && r.getRemainingDuration() > 0){
				setComp(e, new Ability(ability.getName(), true));
				setComp(e, new TriggerRepeater(r.getMaxDuration(),
						r.getPeriod(),
						r.getPeriodRange(),
						r.getRemainingDuration() - LogicLoop.getMillisPerTick(),
						r.getPeriod() + RandomUtil.between(0, r.getPeriodRange())));
			} else {
				setComp(e, new TriggerRepeater(r.getMaxDuration(),
						r.getPeriod(),
						r.getPeriodRange(),
						r.getRemainingDuration() - LogicLoop.getMillisPerTick(),
						r.getRemainingBeforePeriod() - LogicLoop.getMillisPerTick()));
			}
		}
	}

}
