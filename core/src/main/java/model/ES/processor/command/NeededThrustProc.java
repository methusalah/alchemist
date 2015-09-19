package model.ES.processor.command;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class NeededThrustProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarStance.class, PlanarNeededThrust.class, MotionCapacity.class, PlanarVelocityToApply.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime){
		PlanarNeededThrust thrust = e.get(PlanarNeededThrust.class);
		MotionCapacity capacity = e.get(MotionCapacity.class);
		
		Point2D velocity = thrust.getDirection().getScaled(capacity.getThrustPower());
		PlanarVelocityToApply v = e.get(PlanarVelocityToApply.class);
		velocity = velocity.getAddition(v.getVector());
		setComp(e, new PlanarVelocityToApply(velocity));
		removeComp(e, PlanarNeededThrust.class);
	}
}
