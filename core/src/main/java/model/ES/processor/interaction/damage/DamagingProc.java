package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.ToRemove;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.damage.DamageCapacity;
import model.ES.component.interaction.Damaging;
import util.LogUtil;

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
			switch(capacity.getType()){
			case BASIC : att = DamageApplier.applyBasic(att, capacity.getBase()); break; 
			case INCENDIARY : att = DamageApplier.applyIncendiary(att, capacity.getBase()); break; 
			case CORROSIVE : att = DamageApplier.applyCorrosive(att, capacity.getBase()); break; 
			case SHOCK : att = DamageApplier.applyShock(att, capacity.getBase()); break; 
			}
			entityData.setComponent(damaging.target, att);
		}
		setComp(e, new ToRemove());
	}
}
