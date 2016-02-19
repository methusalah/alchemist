package processor.logic.motion;

import com.simsilica.es.Entity;

import model.ECS.pipeline.Processor;

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
