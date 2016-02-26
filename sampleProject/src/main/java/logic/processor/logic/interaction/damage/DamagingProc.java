package logic.processor.logic.interaction.damage;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.combat.damage.DamageCapacity;
import component.combat.damage.Damaging;
import component.combat.resistance.Attrition;
import component.combat.resistance.Shield;
import component.lifeCycle.ToRemove;

public class DamagingProc extends BaseProcessor {
	
	@Override
	protected void registerSets() {
		registerDefault(Damaging.class, DamageCapacity.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		Damaging damaging = e.get(Damaging.class);
		Attrition att = entityData.getComponent(damaging.target, Attrition.class);
		if(att != null){
			// the target is damageable by attrition
			DamageCapacity capacity = e.get(DamageCapacity.class);
			DamageApplier applier = new DamageApplier(att, capacity.getType(), capacity.getBase());
			entityData.setComponent(damaging.target, applier.getResult());
			
			if(applier.getDamageOnShield() > 0){
				DamageFloatingLabelCreator.create(entityData, damaging.target, capacity.getType(), applier.getDamageOnShield(), true, false);
				Shield shield = entityData.getComponent(damaging.target, Shield.class);
				if(shield != null)
					entityData.setComponent(damaging.target, new Shield(shield.getCapacity(), shield.getRechargeRate(), shield.getRechargeDelay(), shield.getRechargeDelay()));
			}
			if(applier.getDamageOnHitPoints() > 0)
				DamageFloatingLabelCreator.create(entityData, damaging.target, capacity.getType(), applier.getDamageOnHitPoints(), false, false);
		}
		setComp(e, new ToRemove());
	}
}
