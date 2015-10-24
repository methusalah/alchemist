package model.ES.processor.shipGear;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.commonLogic.Controlling;
import model.ES.component.assets.Thruster;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.hierarchy.Parenting;
import model.ES.component.motion.PlanarStance;
import util.math.AngleUtil;
import util.math.Fraction;

public class ThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Thruster.class, Parenting.class);
	}

	@Override
	protected void onEntityEachTick(Entity e) {
		Thruster thruster = e.get(Thruster.class);
		Parenting parenting = e.get(Parenting.class);
		EntityId holder = parenting.getParent();

		PlanarNeededThrust parentThrust = Controlling.getControl(PlanarNeededThrust.class, e.getId(), entityData);
		PlanarStance parentStance = Controlling.getControl(PlanarStance.class, e.getId(), entityData);
		
		double activationRate = 0;
		if(parentThrust != null && !parentThrust.getDirection().isOrigin()){
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
