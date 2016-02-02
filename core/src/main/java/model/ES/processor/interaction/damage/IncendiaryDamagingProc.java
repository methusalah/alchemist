package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.ToRemove;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.damage.BasicDamageCapacity;
import model.ES.component.assets.damage.BasicDamageCapacity.DamageType;
import model.ES.component.assets.damage.IncendiaryDamageCapacity;
import model.ES.component.interaction.Damaging;

public class IncendiaryDamagingProc extends Processor {
	private final static double ON_FLESH = 1.2;
	private final static double ON_SHIELD = 0.5;
	private final static double ON_ARMOR = 0.8;

	@Override
	protected void registerSets() {
		registerDefault(Damaging.class, IncendiaryDamageCapacity.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		Damaging damaging = e.get(Damaging.class);
		Attrition attrition = entityData.getComponent(damaging.target, Attrition.class);
		if(attrition != null){
			// the target is damagable by attrition
			IncendiaryDamageCapacity capacity = e.get(IncendiaryDamageCapacity.class);
			entityData.setComponent(damaging.target, DamageApplier.apply(attrition, capacity.getBase(), ON_SHIELD, ON_ARMOR, ON_FLESH));
			
		}
		setComp(e, new ToRemove());
	}
}
