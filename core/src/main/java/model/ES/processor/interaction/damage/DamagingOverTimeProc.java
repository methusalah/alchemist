package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.ToRemove;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.damage.BasicDamageCapacity;
import model.ES.component.assets.damage.BasicDamageCapacity.DamageType;
import model.ES.component.interaction.Damaging;
import util.math.RandomUtil;

public class DamagingOverTimeProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Damaging.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		Damaging damaging = e.get(Damaging.class);
		BasicDamageCapacity capacity = e.get(BasicDamageCapacity.class);
		
		Attrition att = entityData.getComponent(damaging.target, Attrition.class);
		if(att != null){
			double chance = 0;
			// the target is damageable by attrition
			// we first compute the damage
			DamageType dmgType;
			if(capacity.getShockChance() != 0)
				chance = capacity.getShockChance();
			else if(capacity.getCorrosiveChance() != 0)
				chance = capacity.getCorrosiveChance();
			else if(capacity.getIncendiaryChance() != 0)
				chance = capacity.getIncendiaryChance();
			
			if(chance == 0 || RandomUtil.next() > chance)
				return;
			else {
				
			}
			
		}
		setComp(e, new ToRemove());
	}
	
	/**
	 * Returns base damage remaining after shield damaging
	 *  
	 * @param dmgType
	 * @param base
	 * @param att
	 * @return
	 */
	private int damageShield(DamageType dmgType, int base, Attrition att, EntityId target){
		if(att.getActualShield() > 0){
			int shield = att.getActualShield(); 
			double damageOnShield = base;
			switch(dmgType){
			case SHOCK : damageOnShield *= SHOCK_SHIELD; break;
			case CORROSIVE : damageOnShield *= CORROSIVE_SHIELD; break;
			case INCENDIARY : damageOnShield *= INCENDIARY_SHIELD; break;
			default : break;
			}
			shield -= damageOnShield;
			entityData.setComponent(target, new Attrition(att.getMaxHitpoints(),
					att.getActualHitpoints(),
					att.getMaxShield(),
					Math.max(0, shield),
					att.isArmored()));
			if(shield < 0){
				double remaining = -shield;
				remaining /= damageOnShield/base;
				return base - (int)Math.round(remaining);
			} else
				return 0;
		} else
			return base;
	}
	
	private void damageHitPoints(DamageType dmgType, int base, Attrition att, EntityId target){
		double damageOnHitPoints = base;
		if(att.isArmored())
			switch(dmgType){
			case SHOCK : damageOnHitPoints *= SHOCK_ARMOR; break;
			case CORROSIVE : damageOnHitPoints *= CORROSIVE_ARMOR; break;
			case INCENDIARY : damageOnHitPoints *= INCENDIARY_ARMOR; break;
			default : break;
			}
		else
			switch(dmgType){
			case SHOCK : damageOnHitPoints *= SHOCK_FLESH; break;
			case CORROSIVE : damageOnHitPoints *= CORROSIVE_FLESH; break;
			case INCENDIARY : damageOnHitPoints *= INCENDIARY_FLESH; break;
			default : break;
			}
		entityData.setComponent(target, new Attrition(att.getMaxHitpoints(),
				Math.max(0, att.getActualHitpoints() - (int)Math.round(damageOnHitPoints)),
				att.getMaxShield(),
				att.getActualShield(),
				att.isArmored()));
	}
}
