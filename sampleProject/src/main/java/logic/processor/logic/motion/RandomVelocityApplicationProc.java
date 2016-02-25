package logic.processor.logic.motion;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;

import component.motion.PlanarVelocityToApply;
import component.motion.RandomVelocityToApply;
import component.motion.physic.Physic;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import util.math.RandomUtil;

public class RandomVelocityApplicationProc extends Processor {
	@Override
	protected void registerSets() {
		registerDefault(Physic.class, RandomVelocityToApply.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		Physic ph = e.get(Physic.class);
		RandomVelocityToApply toApply = e.get(RandomVelocityToApply.class);

		double force = toApply.getForce() + RandomUtil.between(0, toApply.getForceRange());
		Point2D velocityToApply = Point2D.UNIT_X.getScaled(force);
		
		velocityToApply = velocityToApply.getRotation(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT));
		velocityToApply = velocityToApply.getMult(1/ph.getMass());
		
		setComp(e, new PlanarVelocityToApply(velocityToApply));
		removeComp(e, RandomVelocityToApply.class);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
