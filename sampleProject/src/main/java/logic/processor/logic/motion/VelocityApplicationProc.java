package logic.processor.logic.motion;

import com.simsilica.es.Entity;

import component.motion.PlanarStance;
import component.motion.PlanarVelocityToApply;
import component.motion.physic.Physic;
import model.ECS.pipeline.Pipeline;
import model.ECS.pipeline.Processor;
import util.geometry.geom2d.Point2D;

public class VelocityApplicationProc extends Processor {
	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, Physic.class, PlanarVelocityToApply.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Physic ph = e.get(Physic.class);
		PlanarStance stance = e.get(PlanarStance.class);

		Point2D velocityToApply = e.get(PlanarVelocityToApply.class).vector;
		if(ph.getMass() > 1)
			velocityToApply = velocityToApply.getMult(1/ph.getMass());
		
		Point2D newVelocity = ph.getVelocity().getAddition(velocityToApply);
		//newVelocity = newVelocity.getTruncation(capacity.maxSpeed);
		Point2D newCoord = stance.coord.getAddition(newVelocity.getMult(Pipeline.getSecondPerTick()));

		setComp(e, new Physic(newVelocity, ph.getType(), ph.getExceptions(), ph.getMass(), ph.getRestitution(), ph.getSpawnerException()));
		setComp(e, new PlanarStance(newCoord, stance.orientation, stance.elevation, stance.upVector));
		setComp(e, new PlanarVelocityToApply(Point2D.ORIGIN));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
