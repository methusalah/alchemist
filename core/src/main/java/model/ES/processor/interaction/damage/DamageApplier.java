package model.ES.processor.interaction.damage;

import model.ES.component.assets.Attrition;
import util.LogUtil;

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

	public static Attrition applyBasic(Attrition attrition, int base){
		return apply(attrition, base, BASE_FLESH, BASE_ARMOR, BASE_SHIELD);
	}
	
	public static Attrition applyIncendiary(Attrition attrition, int base){
		return apply(attrition, base, INCENDIARY_FLESH, INCENDIARY_ARMOR, INCENDIARY_SHIELD);
	}
	
	public static Attrition applyCorrosive(Attrition attrition, int base){
		return apply(attrition, base, CORROSIVE_FLESH, CORROSIVE_ARMOR, CORROSIVE_SHIELD);
	}

	public static Attrition applyShock(Attrition attrition, int base){
		return apply(attrition, base, SHOCK_FLESH, SHOCK_ARMOR, SHOCK_SHIELD);
	}
	
	private static Attrition apply(Attrition attrition, int base, double fleshModifier, double armorModifier, double shieldModifier){
		// damage shield
		if(attrition.getActualShield() > 0){
			int shield = attrition.getActualShield();
			int modified = (int)Math.round(base * shieldModifier); 
			shield -= modified;
			
			LogUtil.info("DamageApplier.apply shield amount : " + modified);
			LogUtil.info("DamageApplier.apply shield  : " + shield);
			// compute the remaining base damage
			if(shield < 0){
				base = -shield;
				base = (int)Math.round((double)base / shieldModifier);
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
		
		LogUtil.info("DamageApplier.apply hit points amount : " + modified);
		LogUtil.info("DamageApplier.apply hit points  : " + (attrition.getActualHitpoints() - (int)Math.round(modified)));
		return new Attrition(attrition.getMaxHitpoints(),
				Math.max(0, attrition.getActualHitpoints() - (int)Math.round(modified)),
				attrition.getMaxShield(),
				attrition.getActualShield(),
				attrition.isArmored());
	}
}
