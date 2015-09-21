package model.ES.processor.motion;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Physic;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class VelocityApplicationProc extends Processor {
	@Override
	protected void registerSets() {
		register(PlanarStance.class, Physic.class, PlanarVelocityToApply.class, MotionCapacity.class);
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
		Physic ph = e.get(Physic.class);
		PlanarStance stance = e.get(PlanarStance.class);
		MotionCapacity capacity = e.get(MotionCapacity.class);

		Point2D velocityToApply = e.get(PlanarVelocityToApply.class).getVector();
		velocityToApply = velocityToApply.getMult(1/ph.stat.mass);
		
		Point2D newVelocity = ph.velocity.getAddition(velocityToApply);
		newVelocity = newVelocity.getTruncation(capacity.getMaxSpeed());
		Point2D newCoord = stance.getCoord().getAddition(newVelocity.getMult(elapsedTime));

		setComp(e, new Physic(newVelocity, ph.stat, ph.spawnerException));
		setComp(e, new PlanarStance(newCoord, stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
		setComp(e, new PlanarVelocityToApply(Point2D.ORIGIN));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
