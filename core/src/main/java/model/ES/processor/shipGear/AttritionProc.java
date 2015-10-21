package model.ES.processor.shipGear;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.LifeTime;
import model.ES.component.ToRemove;
import model.ES.component.assets.Attrition;
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
		if(a.actualHitpoints <= 0){
			setComp(e, new ToRemove());
			if(a.getSpawnOnDeath() != null){
				EntityId spawned = PrototypeCreator.create(a.getSpawnOnDeath(), null);
				if(entityData.getComponent(spawned, LifeTime.class) != null)
					entityData.setComponent(spawned, new LifeTime(System.currentTimeMillis(), entityData.getComponent(spawned, LifeTime.class).getDuration()));
				
				PlanarStance eStance = entityData.getComponent(e.getId(), PlanarStance.class);
				if(eStance != null && entityData.getComponent(spawned, PlanarStance.class) != null)
					entityData.setComponent(spawned, new PlanarStance(eStance.getCoord(), eStance.getOrientation(), eStance.getElevation(), eStance.getUpVector()));
			}
		}
	}
}
