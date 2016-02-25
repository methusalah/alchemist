package logic.processor.logic.command;

import com.simsilica.es.Entity;

import component.motion.MotionCapacity;
import component.motion.PlanarNeededThrust;
import component.motion.PlanarStance;
import component.motion.PlanarVelocityToApply;
import model.ECS.pipeline.Processor;
import util.geometry.geom2d.Point2D;

public class NeededThrustProc extends Processor {
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, PlanarNeededThrust.class, MotionCapacity.class, PlanarVelocityToApply.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarNeededThrust thrust = e.get(PlanarNeededThrust.class);
		MotionCapacity capacity = e.get(MotionCapacity.class);
		PlanarStance stance = e.get(PlanarStance.class);
		
		// orient the velocity according to the origin to scale its X and Y components
		Point2D originalVel = thrust.getDirection().getRotation(-stance.orientation.getValue()).getNormalized();
		double velX = originalVel.x * (originalVel.x > 0? capacity.thrustPower : capacity.frontalThrustPower);
		double velY = originalVel.y * capacity.lateralThrustPower;
		
		Point2D velocity = new Point2D(velX, velY).getRotation(stance.orientation.getValue());

		// adding the thrust velocity to the velocity to apply.
		PlanarVelocityToApply v = e.get(PlanarVelocityToApply.class);
		velocity = velocity.getAddition(v.vector);
		setComp(e, new PlanarVelocityToApply(velocity));
//		removeComp(e, PlanarNeededThrust.class);
	}
}
