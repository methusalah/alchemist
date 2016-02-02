package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;

import controller.ECS.LogicLoop;
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
			// target is damageable by attrition
			DamageOverTime dot = e.get(DamageOverTime.class);
			int timeSinceLastTick = dot.getTimeSinceLastTick();
			
			int timePerTick = (int)Math.round(1000d/dot.getTickPerSecond());
			if(dot.getTimeSinceLastTick() > timePerTick){
				timeSinceLastTick -= timePerTick;
				int damagePerTick = dot.getAmountPerSecond()/dot.getTickPerSecond();
				switch(dot.getType()){
				case BASIC : att = DamageApplier.applyBasic(att, damagePerTick); break; 
				case INCENDIARY : att = DamageApplier.applyIncendiary(att, damagePerTick); break; 
				case CORROSIVE : att = DamageApplier.applyCorrosive(att, damagePerTick); break; 
				case SHOCK : att = DamageApplier.applyShock(att, damagePerTick); break; 
				}
				entityData.setComponent(damaging.target, att);
			}
			timeSinceLastTick += LogicLoop.getMillisPerTick();
			setComp(e, new DamageOverTime(dot.getType(), dot.getAmountPerSecond(), dot.getTickPerSecond(), timeSinceLastTick));
		} else
			setComp(e, new ToRemove());
	}
}
