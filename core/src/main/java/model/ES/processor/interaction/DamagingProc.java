package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.ToRemove;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.DamageCapacity;
import model.ES.component.interaction.Damaging;

public class DamagingProc extends Processor {
	private final static double CORROSIVE_FLESH = 0.8;
	private final static double INCENDIARY_FLESH = 1.2;
	private final static double SHOCK_FLESH = 0.9;

	private final static double CORROSIVE_SHIELD = 0.8;
	private final static double INCENDIARY_SHIELD = 0.5;
	private final static double SHOCK_SHIELD = 1.5;

	private final static double CORROSIVE_ARMOR = 2;
	private final static double INCENDIARY_ARMOR = 0.8;
	private final static double SHOCK_ARMOR = 0.9;

	
	private enum DamageType {NORMAL, INCENDIARY, SHOCK, CORROSIVE, EXPLOSIVE}

	@Override
	protected void registerSets() {
		registerDefault(Damaging.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		Damaging damaging = e.get(Damaging.class);
		DamageCapacity capacity = e.get(DamageCapacity.class);
		
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
