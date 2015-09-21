package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;
import model.ES.component.interaction.Damaging;
import model.ES.component.shipGear.Attrition;

public class DamagingProc extends Processor {

	@Override
	protected void registerSets() {
		register(Damaging.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		Damaging dmg = e.get(Damaging.class);
		Attrition targetStats = entityData.getComponent(dmg.target, Attrition.class);
		if(targetStats != null){
			int remaining = targetStats.actualHitpoints - dmg.damage.amount;
			entityData.setComponent(dmg.target, new Attrition(targetStats.maxHitpoints, remaining));
		}
	}
}
