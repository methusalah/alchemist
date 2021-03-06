package logic.processor.logic.shipGear;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.motion.PlanarNeededRotation;
import component.motion.RotationThruster;
import component.motion.ThrustControl;
import logic.commonLogic.Controlling;
import util.math.Fraction;

public class RotationThrusterProc extends BaseProcessor {

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
