package model.ES.processor.shipGear;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import main.java.model.ECS.pipeline.Processor;
import model.ES.component.combat.resistance.Attrition;
import model.ES.component.lifeCycle.LifeTime;
import model.ES.component.lifeCycle.ToRemove;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.PrototypeCreator;

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
