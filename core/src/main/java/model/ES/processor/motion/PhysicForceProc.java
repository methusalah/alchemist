package model.ES.processor.motion;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.motion.Physic;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.PhysicForce;
import util.geometry.geom2d.Point2D;

public class PhysicForceProc extends Processor {

	@Override
	protected void registerSets() {
		register(PlanarStance.class, PhysicForce.class);
		register(PlanarStance.class, Physic.class, PlanarVelocityToApply.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
		for(Entity impE : sets.get(0))
			for(Entity phE : sets.get(1)){
				PlanarStance impStance = impE.get(PlanarStance.class);
				PlanarStance phStance = phE.get(PlanarStance.class);

				Point2D vector = phStance.getCoord().getSubtraction(impStance.getCoord());
				double distance = vector.getLength(); 
				PhysicForce impulse = impE.get(PhysicForce.class);
				if(distance < impulse.getRadius()){
					double rate = 1-(distance/impulse.getRadius());
					double force = impulse.getForce()*rate;
					Point2D velocity = Point2D.ORIGIN.getTranslation(vector.getAngle(), force);
					PlanarVelocityToApply v = phE.get(PlanarVelocityToApply.class);
					velocity = velocity.getAddition(v.getVector());
					setComp(phE, new PlanarVelocityToApply(velocity));
				}
			}
	}

}
