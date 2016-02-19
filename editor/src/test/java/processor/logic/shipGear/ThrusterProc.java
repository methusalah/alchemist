package test.java.processor.logic.shipGear;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import main.java.model.ECS.pipeline.Processor;
import model.ECS.Parenting;
import model.ES.commonLogic.Controlling;
import model.ES.component.motion.PlanarNeededThrust;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.ThrustControl;
import model.ES.component.motion.Thruster;
import util.math.AngleUtil;
import util.math.Fraction;

public class ThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Thruster.class, ThrustControl.class);
	}

	@Override
	protected void onEntityEachTick(Entity e) {
		ThrustControl thrustControl = e.get(ThrustControl.class);
		
		if(thrustControl.isActive()){
			Thruster thruster = e.get(Thruster.class);
			PlanarNeededThrust parentThrust = Controlling.getControl(PlanarNeededThrust.class, e.getId(), entityData);
			if(parentThrust == null)
				return;
			PlanarStance parentStance = Controlling.getControl(PlanarStance.class, e.getId(), entityData);
			if(parentStance == null)
				return;
			
			double activationRate = 0;
			if(!parentThrust.getDirection().isOrigin()){
				double diff = AngleUtil.getSmallestDifference(parentThrust.getDirection().getAngle()-parentStance.orientation.getValue(), thruster.direction.get2D().getAngle());
				if(diff <= thruster.activationAngle.getValue()){
					activationRate = 1;
					if(!thruster.onOff)
						activationRate = 1-(diff/thruster.activationAngle.getValue());
				}
			}
			setComp(e, new Thruster(thruster.direction, thruster.activationAngle, new Fraction(activationRate), thruster.onOff));
		}
	}

}
