package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.LifeTime;
import model.ES.component.ToRemove;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.damage.DamageOverTime;
import model.ES.component.interaction.Damaging;

public class DamagingOverTimeProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Damaging.class, DamageOverTime.class, LifeTime.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Damaging damaging = e.get(Damaging.class);
		Attrition att = entityData.getComponent(damaging.target, Attrition.class);
		if(att != null){
			DamageOverTime dot = e.get(DamageOverTime.class);
//			if(dt.)
		} else
			setComp(e, new ToRemove());
	}
}
