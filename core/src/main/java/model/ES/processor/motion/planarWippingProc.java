package model.ES.processor.motion;

import model.ES.component.debug.VelocityDebugger;
import model.ES.component.planarMotion.PlanarMotionCapacity;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarWipping;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class planarWippingProc extends Processor {
	@Override
	protected void registerSets() {
		register(PlanarMotionCapacity.class, PlanarWipping.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime){
		PlanarWipping wipping = e.get(PlanarWipping.class);
		PlanarStance stance = e.get(PlanarStance.class);
		PlanarMotionCapacity capacity = e.get(PlanarMotionCapacity.class);
		
		Point2D velocity = wipping.getVelocity();

//		double friction = Math.exp(-capacity.getMass());
//		velocity = velocity.getSubtraction(velocity.getMult(friction*elapsedTime));
//		if(velocity.getLength() < 0.0001)
//			velocity = Point2D.ORIGIN;
		double speed = velocity.getLength(); 
		if(speed > 0){
			double energy = speed*capacity.getMass();
			double drag = speed*speed * wipping.getDragging();
			double remainingEnergy = energy-drag;
			velocity = velocity.getScaled(speed*remainingEnergy/energy);
			if(velocity.getLength() < 0.0001)
				velocity = Point2D.ORIGIN;
		}
		
		Point2D massedVelocity = velocity.getMult(capacity.getMass());
		Point2D resulting = massedVelocity.getAddition(wipping.getAppliedVelocity().getMult(capacity.getThrustPower()));
		velocity = resulting.getDivision(capacity.getMass());
		velocity = velocity.getTruncation(capacity.getMaxSpeed());
		
		setComp(e, new PlanarWipping(velocity, wipping.getDragging()));
		setComp(e, new PlanarStance(stance.getCoord().getAddition(velocity.getMult(elapsedTime)), stance.getOrientation(), stance.getElevation(), stance.getUpVector()));

		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		sb.append("    velocity length : "+ wipping.getVelocity().getLength() + System.lineSeparator());
		sb.append("    appliedVelocity length : " + wipping.getAppliedVelocity().getLength() + System.lineSeparator());
		app.getDebugger().add(sb.toString());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
