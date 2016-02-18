package model.ES.processor.shipGear;

import model.ECS.Parenting;
import model.ES.commonLogic.Controlling;
import model.ES.component.motion.PlanarNeededRotation;
import model.ES.component.motion.RotationThruster;
import model.ES.component.motion.ThrustControl;
import util.LogUtil;
import util.math.Fraction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import main.java.model.ECS.pipeline.Processor;

public class RotationThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(RotationThruster.class, ThrustControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		ThrustControl thrustControl = e.get(ThrustControl.class);
		
		if(thrustControl.isActive()){
			PlanarNeededRotation rotation = Controlling.getControl(PlanarNeededRotation.class, e.getId(), entityData);
			if(rotation == null)
				return;
			
			RotationThruster thruster = e.get(RotationThruster.class);
			
			double activationRate = 0;
			if(rotation != null){
				if(rotation.angle.getValue() > 0.08 && !thruster.clockwise 
						|| rotation.angle.getValue() < -0.08 && thruster.clockwise){
					activationRate = 1;
					if(!thruster.onOff && Math.abs(rotation.angle.getValue()) < thruster.maxAngle)
						activationRate = Math.abs(rotation.angle.getValue())/thruster.maxAngle;
				}
			}
			setComp(e, new RotationThruster(thruster.clockwise, thruster.maxAngle, new Fraction(activationRate), thruster.onOff));
		}
			
	}

}
