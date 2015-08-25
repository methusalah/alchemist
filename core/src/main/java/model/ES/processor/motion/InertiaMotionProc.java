package model.ES.processor.motion;

import util.LogUtil;
import util.math.AngleUtil;
import model.ES.component.motion.PlanarIntendedMotion;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlanarMotion;
import model.ES.component.motion.PlayerOrder;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class InertiaMotionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarMotionCapacity.class, PlanarInertia.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		PlanarInertia inertia = e.get(PlanarInertia.class);
		PlanarMotionCapacity capacity = e.get(PlanarMotionCapacity.class);
		double speed = inertia.getSpeed();
		double maxSpeed = capacity.getMaxSpeed();
		double acceleration = capacity.getAcceleration();
		double deceleration = capacity.getDeceleration();
		LogUtil.info("inertia " + e.getId());

		if(inertia.isAccelerate()){
			if(speed < maxSpeed){
				speed = speed + acceleration*elapsedTime;
				speed = Math.min(maxSpeed, speed);
			}
			LogUtil.info("accelerate : "+speed);
		} else {
			if(speed > 0){
				speed = speed - deceleration*elapsedTime;
				speed = Math.max(0, speed);
			}
			LogUtil.info("decelerate : "+speed);
		}
		
			
		PlanarInertia resultingInertia = new PlanarInertia(speed, false);

		setComp(e, resultingInertia);
		removeComp(e, PlanarIntendedMotion.class);
	}
}
