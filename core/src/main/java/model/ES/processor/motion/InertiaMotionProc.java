package model.ES.processor.motion;

import model.ES.component.planarMotion.PlanarMotionCapacity;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarVelocityInertiaDebugger;
import model.ES.component.planarMotion.PlanarWippingInertia;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class InertiaMotionProc extends Processor {
	private static double DRAGGING_FACTOR = 10; 
	
	
	@Override
	protected void registerSets() {
		register(PlanarMotionCapacity.class, PlanarWippingInertia.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		PlanarWippingInertia inertia = e.get(PlanarWippingInertia.class);
		PlanarStance stance = e.get(PlanarStance.class);
		PlanarMotionCapacity capacity = e.get(PlanarMotionCapacity.class);
		
		Point2D velocity = inertia.getVelocity();

//		double friction = Math.exp(-capacity.getMass());
//		velocity = velocity.getSubtraction(velocity.getMult(friction*elapsedTime));
//		if(velocity.getLength() < 0.0001)
//			velocity = Point2D.ORIGIN;
		double speed = velocity.getLength(); 
		if(speed > 0){
			double energy = speed*capacity.getMass();
			double drag = speed*speed * DRAGGING_FACTOR;
			double remainingEnergy = energy-drag;
			velocity = velocity.getScaled(speed*remainingEnergy/energy);
			if(velocity.getLength() < 0.0001)
				velocity = Point2D.ORIGIN;
		}
		
		Point2D massedVelocity = velocity.getMult(capacity.getMass());
		Point2D resulting = massedVelocity.getAddition(inertia.getAppliedVelocity().getMult(capacity.getThrustPower()));
		velocity = resulting.getDivision(capacity.getMass());
		
//		velocity = velocity.getAddition(inertia.getAppliedVelocity());//.getMult(capacity.getThrustPower() / capacity.getMass()));
		velocity = velocity.getTruncation(capacity.getMaxSpeed()*elapsedTime);
		
		PlanarWippingInertia resultingInertia = new PlanarWippingInertia(velocity);

		setComp(e, resultingInertia);
		setComp(e, new PlanarStance(stance.getCoord().getAddition(velocity), stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
		
		// debug
		setComp(e, new PlanarVelocityInertiaDebugger(inertia.getVelocity().getDivision(elapsedTime), inertia.getAppliedVelocity().getDivision(elapsedTime), velocity.getDivision(elapsedTime)));
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		sb.append("    velocity length : "+ inertia.getVelocity().getLength() + System.lineSeparator());
		sb.append("    appliedVelocity length : " + inertia.getAppliedVelocity().getLength() + System.lineSeparator());
		app.getDebugger().add(sb.toString());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
