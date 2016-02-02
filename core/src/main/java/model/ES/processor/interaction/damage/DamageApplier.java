package model.ES.processor.interaction.damage;

import model.ES.component.assets.Attrition;

public class DamageApplier {
	public static Attrition apply(Attrition attrition, int base, double shieldModifier, double armorModifier, double fleshModifier){
		
		// damage shield
		if(attrition.getActualShield() > 0){
			int shield = attrition.getActualShield();
			int modified = (int)Math.round(base * shieldModifier); 
			shield -= modified;
			
			// compute the remaining base damage
			if(shield < 0){
				double remaining = -shield;
				remaining /= shieldModifier;
				remaining -= (int)Math.round(remaining);
			} else
				base = 0;
			
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
			modified = (int)Math.round(base * armorModifier); 
		else
			modified = (int)Math.round(base * fleshModifier);
		
		return new Attrition(attrition.getMaxHitpoints(),
				Math.max(0, attrition.getActualHitpoints() - (int)Math.round(modified)),
				attrition.getMaxShield(),
				attrition.getActualShield(),
				attrition.isArmored());
	}
}
