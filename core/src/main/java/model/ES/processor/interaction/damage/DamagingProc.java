package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.combat.damage.DamageCapacity;
import model.ES.component.combat.damage.Damaging;
import model.ES.component.combat.resistance.Attrition;
import model.ES.component.combat.resistance.Shield;
import model.ES.component.lifeCycle.ToRemove;

public class DamagingProc extends Processor {
	
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
