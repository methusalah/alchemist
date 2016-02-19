package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;

import main.java.model.ECS.pipeline.Processor;
import model.ES.component.combat.damage.DamageOverTime;
import model.ES.component.combat.damage.Damaging;
import model.ES.component.combat.resistance.Attrition;
import model.ES.component.combat.resistance.Shield;
import model.ES.component.lifeCycle.LifeTime;
import model.ES.component.lifeCycle.ToRemove;
import model.tempImport.LogicLoop;

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
			// target is damageable by attrition
			DamageOverTime dot = e.get(DamageOverTime.class);
			int timeSinceLastTick = dot.getTimeSinceLastTick();
			
			int timePerTick = (int)Math.round(1000d/dot.getTickPerSecond());
			if(timeSinceLastTick > timePerTick){
				timeSinceLastTick -= timePerTick;
				int damagePerTick = dot.getAmountPerSecond()/dot.getTickPerSecond();
				
				DamageApplier applier = new DamageApplier(att, dot.getType(), damagePerTick);
				entityData.setComponent(damaging.target, applier.getResult());

				if(applier.getDamageOnShield() > 0){
					DamageFloatingLabelCreator.create(entityData, damaging.target, dot.getType(), applier.getDamageOnShield(), true, true);
					Shield shield = entityData.getComponent(damaging.target, Shield.class);
					if(shield != null)
						entityData.setComponent(damaging.target, new Shield(shield.getCapacity(), shield.getRechargeRate(), shield.getRechargeDelay(), shield.getRechargeDelay()));
				}					
				if(applier.getDamageOnHitPoints() > 0)
					DamageFloatingLabelCreator.create(entityData, damaging.target, dot.getType(), applier.getDamageOnHitPoints(), false, true);

			}
			timeSinceLastTick += LogicLoop.getMillisPerTick();
			setComp(e, new DamageOverTime(dot.getType(), dot.getAmountPerSecond(), dot.getTickPerSecond(), timeSinceLastTick));
		} else
			setComp(e, new ToRemove());
	}
}
