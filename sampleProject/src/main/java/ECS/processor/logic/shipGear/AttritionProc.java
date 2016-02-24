package ECS.processor.logic.shipGear;

import com.simsilica.es.Entity;

import ECS.component.combat.resistance.Attrition;
import ECS.component.lifeCycle.ToRemove;
import model.ECS.pipeline.Processor;

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
