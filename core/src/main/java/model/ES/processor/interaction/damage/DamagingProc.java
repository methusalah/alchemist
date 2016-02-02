package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.ToRemove;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.damage.BasicDamageCapacity;
import model.ES.component.assets.damage.BasicDamageCapacity.DamageType;
import model.ES.component.interaction.Damaging;

public class DamagingProc extends Processor {
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
			// the target is damageable by attrition
			// we first compute the damage
			DamageType dmgType;
			if(capacity.getShockChance() == 0)
				dmgType = DamageType.SHOCK;
			else if(capacity.getCorrosiveChance() == 0)
				dmgType = DamageType.CORROSIVE;
			else if(capacity.getIncendiaryChance() == 0)
				dmgType = DamageType.INCENDIARY;
			else if(capacity.isExplosive())
				dmgType = DamageType.EXPLOSIVE;
			else dmgType = DamageType.NORMAL;
			
			
			int base = capacity.getBase();
			int baseAfterShield = damageShield(dmgType, base, att, damaging.target);
			if(baseAfterShield > 0)
				damageHitPoints(dmgType, baseAfterShield, att, damaging.target);
			
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
			case SHOCK : damageOnShield *= BasicDamageCapacity.SHOCK_SHIELD; break;
			case CORROSIVE : damageOnShield *= BasicDamageCapacity.CORROSIVE_SHIELD; break;
			case INCENDIARY : damageOnShield *= BasicDamageCapacity.INCENDIARY_SHIELD; break;
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
			case SHOCK : damageOnHitPoints *= BasicDamageCapacity.SHOCK_ARMOR; break;
			case CORROSIVE : damageOnHitPoints *= BasicDamageCapacity.CORROSIVE_ARMOR; break;
			case INCENDIARY : damageOnHitPoints *= BasicDamageCapacity.INCENDIARY_ARMOR; break;
			default : break;
			}
		else
			switch(dmgType){
			case SHOCK : damageOnHitPoints *= BasicDamageCapacity.SHOCK_FLESH; break;
			case CORROSIVE : damageOnHitPoints *= BasicDamageCapacity.CORROSIVE_FLESH; break;
			case INCENDIARY : damageOnHitPoints *= BasicDamageCapacity.INCENDIARY_FLESH; break;
			default : break;
			}
		entityData.setComponent(target, new Attrition(att.getMaxHitpoints(),
				Math.max(0, att.getActualHitpoints() - (int)Math.round(damageOnHitPoints)),
				att.getMaxShield(),
				att.getActualShield(),
				att.isArmored()));
	}
}
