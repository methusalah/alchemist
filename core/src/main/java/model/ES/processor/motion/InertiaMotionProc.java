package model.ES.processor.motion;

import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarInertiaDebugger;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarStance;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class InertiaMotionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarMotionCapacity.class, PlanarInertia.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		PlanarInertia inertia = e.get(PlanarInertia.class);
		PlanarStance stance = e.get(PlanarStance.class);
		PlanarMotionCapacity capacity = e.get(PlanarMotionCapacity.class);
		
		Point2D velocity = inertia.getVelocity();
		double friction = Math.exp(-capacity.getMass());
		velocity = velocity.getSubtraction(velocity.getMult(friction*elapsedTime));
		if(velocity.getLength() < 0.0001)
			velocity = Point2D.ORIGIN;
		velocity = velocity.getAddition(inertia.getAppliedVelocity().getMult(capacity.getThrustPower() / capacity.getMass()));
		velocity = velocity.getTruncation(capacity.getMaxSpeed());
		
		PlanarInertia resultingInertia = new PlanarInertia(velocity);

		setComp(e, resultingInertia);
		setComp(e, new PlanarStance(stance.getCoord().getAddition(velocity), stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
		
		// debug
		setComp(e, new PlanarInertiaDebugger(inertia.getVelocity().getMult(elapsedTime), inertia.getAppliedVelocity(), velocity.getMult(elapsedTime)));
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		sb.append("    velocity length : "+ inertia.getVelocity().getLength() + System.lineSeparator());
		sb.append("    appliedVelocity length : " + inertia.getAppliedVelocity().getLength() + System.lineSeparator());
		app.getDebugger().add(sb.toString());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
