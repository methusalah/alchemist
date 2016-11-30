package logic.processor.logic.interaction.damage;

import component.combat.damage.DamageType;
import component.combat.resistance.Attrition;

public class DamageApplier {
	private final static double BASE_FLESH = 1;
	private final static double BASE_ARMOR = 0.8;
	private final static double BASE_SHIELD = 1;

	private final static double INCENDIARY_FLESH = 1.2;
	private final static double INCENDIARY_ARMOR = 0.8;
	private final static double INCENDIARY_SHIELD = 0.5;

	private final static double CORROSIVE_FLESH = 0.8;
	private final static double CORROSIVE_SHIELD = 0.8;
	private final static double CORROSIVE_ARMOR = 2;

	private final static double SHOCK_FLESH = 0.9;
	private final static double SHOCK_ARMOR = 0.9;
	private final static double SHOCK_SHIELD = 1.5;

	
	private int damageOnShield, damageOnHitPoints;
	private Attrition result;
	
	public DamageApplier(Attrition attrition, DamageType type, int base) {
		int remaining = base;
		// creating modifiers
		double fleshModifier, armorModifier, shieldModifier;
		switch(type){
		case BASIC :
			fleshModifier = BASE_FLESH;
			armorModifier = BASE_ARMOR;
			shieldModifier = BASE_SHIELD;
			break; 
		case INCENDIARY :
			fleshModifier = INCENDIARY_FLESH;
			armorModifier = INCENDIARY_ARMOR;
			shieldModifier = INCENDIARY_SHIELD;
			break; 
		case CORROSIVE :
			fleshModifier = CORROSIVE_FLESH;
			armorModifier = CORROSIVE_ARMOR;
			shieldModifier = CORROSIVE_SHIELD;
			break; 
		case SHOCK :
			fleshModifier = SHOCK_FLESH;
			armorModifier = SHOCK_ARMOR;
			shieldModifier = SHOCK_SHIELD;
			break; 
		default : throw new RuntimeException("damage type unkown : " + type);
		}
		
		// damage shield
		if(attrition.getActualShield() > 0){
			int shield = attrition.getActualShield();
			int modified = (int)Math.round(remaining * shieldModifier); 
			damageOnShield = Math.min(shield, modified);

			shield -= modified;
			// compute the remaining base damage
			if(shield < 0){
				remaining = -shield;
				remaining = (int)Math.round(remaining / shieldModifier);
			} else
				remaining = 0;
			
			// creation of the modified attrition
			attrition = new Attrition(attrition.getMaxHitpoints(),
					attrition.getActualHitpoints(),
					attrition.getMaxShield(),
					Math.max(0, shield),
					attrition.isArmored());
		}

		// damage hit points
		int modified; 
		if(attrition.isArmored())
			modified = (int)Math.round(remaining * armorModifier); 
		else
			modified = (int)Math.round(remaining * fleshModifier);
		
		damageOnHitPoints = modified;
		
		result =  new Attrition(attrition.getMaxHitpoints(),
				Math.max(0, attrition.getActualHitpoints() - Math.round(modified)),
				attrition.getMaxShield(),
				attrition.getActualShield(),
				attrition.isArmored());
	}

	public int getDamageOnShield() {
		return damageOnShield;
	}

	public int getDamageOnHitPoints() {
		return damageOnHitPoints;
	}

	public Attrition getResult() {
		return result;
	}
}
