package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.ECS.Processor;
import model.ES.component.ToRemove;
import model.ES.component.assets.Attrition;
import model.ES.component.interaction.Damaging;

public class DamagingProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Damaging.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		Damaging dmg = e.get(Damaging.class);
		Attrition attrition = entityData.getComponent(dmg.target, Attrition.class);
		if(attrition != null){
			int remaining = attrition.actualHitpoints - dmg.damage.amount;
			entityData.setComponent(dmg.target, new Attrition(attrition.maxHitpoints, remaining, attrition.getSpawnOnDeath()));
		}
		setComp(e, new ToRemove());
	}
}
