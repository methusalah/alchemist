package model.ES.processor.motion;

import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import model.ModelManager;
import model.ES.component.motion.PlanarThrust;
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
		register(PlanarMotionCapacity.class, PlanarInertia.class, PlanarPosition.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		PlanarInertia inertia = e.get(PlanarInertia.class);
		PlanarPosition position = e.get(PlanarPosition.class);
		PlanarMotionCapacity capacity = e.get(PlanarMotionCapacity.class);
		
		
		
		
//		double speed = inertia.getSpeed();
//		double maxSpeed = capacity.getMaxSpeed();
//		double acceleration = capacity.getAcceleration();
//		double deceleration = capacity.getDeceleration();
//		String s = "Inertia processor for " + e.getId()+ " Speed : "+inertia.getSpeed();
//
//		if(inertia.isAccelerate()){
//			if(speed < maxSpeed){
//				speed = speed + acceleration*elapsedTime;
//				speed = Math.min(maxSpeed, speed);
//			}
//			s = s + " and accelerate.";
//		} else {
//			if(speed > 0){
//				speed = speed - deceleration*elapsedTime;
//				speed = Math.max(0, speed);
//			}
//			s = s + " and decelerate.";
//		}
//		app.getDebugger().add(s);

		Point2D velocity = inertia.getVelocity();
		if(inertia.getAppliedVelocity().isOrigin()){
			double brake = Math.exp(-capacity.getMass());
			velocity = velocity.getSubtraction(velocity.getMult(brake));
			if(velocity.getLength() < 0.0001)
				velocity = Point2D.ORIGIN;
		} else {
			velocity = velocity.getAddition(inertia.getAppliedVelocity().getMult(capacity.getThrustPower() / capacity.getMass()));
		}
		
		PlanarInertia resultingInertia = new PlanarInertia(velocity);

		setComp(e, resultingInertia);
		setComp(e, new PlanarPosition(position.getPosition().getAddition(velocity), position.getOrientation()));
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		sb.append("    velocity length : "+ inertia.getVelocity().getLength() + System.lineSeparator());
		sb.append("    appliedVelocity length : " + inertia.getAppliedVelocity().getLength() + System.lineSeparator());
		app.getDebugger().add(sb.toString());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
