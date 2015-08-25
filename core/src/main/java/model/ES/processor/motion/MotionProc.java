package model.ES.processor.motion;

import util.LogUtil;
import util.math.AngleUtil;
import model.ES.component.motion.PlanarIntendedMotion;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlanarMotion;
import model.ES.component.motion.PlayerOrder;
import model.battlefield.army.motion.Motion;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class MotionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarIntendedMotion.class, PlanarMotionCapacity.class, PlanarInertia.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime){
		PlanarIntendedMotion motion = e.get(PlanarIntendedMotion.class);
		PlanarInertia inertia = e.get(PlanarInertia.class);
		PlanarMotionCapacity capacity = e.get(PlanarMotionCapacity.class);
		
		double rotation = 0;
		double distance = 0;
		// rotation
		if(motion.getRotation() != 0){
			double maxRotation = capacity.getMaxRotationSpeed() * elapsedTime;
			maxRotation = Math.min(Math.abs(motion.getRotation()), maxRotation);
			rotation = maxRotation*Math.signum(motion.getRotation());
		}
		
		distance = inertia.getSpeed()*elapsedTime;
		
		PlanarMotion resultingMotion = new PlanarMotion(distance, rotation, elapsedTime);
		PlanarInertia updatedInertia = new PlanarInertia(inertia.getSpeed(), motion.getDistance() > 0);
		LogUtil.info("motion");

		setComp(e, resultingMotion);
		setComp(e, updatedInertia);
		removeComp(e, PlanarIntendedMotion.class);
	}
}
