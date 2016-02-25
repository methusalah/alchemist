package logic.processor.logic.shipGear;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;

import component.combat.resistance.Attrition;
import component.lifeCycle.ToRemove;

public class AttritionProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Attrition.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		Attrition a = e.get(Attrition.class);
		if(a.getActualHitpoints() <= 0){
			setComp(e, new ToRemove());
		}
	}
}
