package processor.logic.combat.resistance;

import java.util.ArrayList;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import model.ECS.pipeline.Processor;
import model.tempImport.ColorData;

public class ShieldProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Shield.class, Attrition.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		Shield shield = e.get(Shield.class);
		Attrition att = e.get(Attrition.class);
		
		if(shield.getDelay() > 0)
			setComp(e, new Shield(shield.getCapacity(), shield.getRechargeRate(), shield.getRechargeDelay(), Math.max(0, shield.getDelay()-LogicLoop.getMillisPerTick())));
		else if(att.getActualShield() < shield.getCapacity()){
			int newShield = Math.min(shield.getCapacity(), att.getActualShield() + (int)Math.round((double)shield.getRechargeRate() * LogicLoop.getSecondPerTick())); 
			setComp(e, new Attrition(att.getMaxHitpoints(), att.getActualHitpoints(), att.getMaxShield(), newShield, att.isArmored()));
			
			EntityId eid = entityData.createEntity();
			entityData.setComponent(eid, new Naming("floating shield "));
			entityData.setComponent(eid, entityData.getComponent(e.getId(), PlanarStance.class));
			entityData.setComponent(eid, new FloatingLabel("Shield restored", new ColorData(150, 100, 100, 255), 20));
			entityData.setComponent(eid, new Physic(Point2D.ORIGIN, "", new ArrayList<String>(), 0, new Fraction(0), null));
			entityData.setComponent(eid, new PlanarVelocityToApply(Point2D.UNIT_Y));
			entityData.setComponent(eid, new LifeTime(1500));
		}
	}
	

}
