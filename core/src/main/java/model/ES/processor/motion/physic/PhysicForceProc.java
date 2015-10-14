package model.ES.processor.motion.physic;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Physic;
import model.ES.component.motion.physic.PhysicForce;
import util.geometry.geom2d.Point2D;

public class PhysicForceProc extends Processor {

	@Override
	protected void registerSets() {
		register(PlanarStance.class, PhysicForce.class);
		register(PlanarStance.class, Physic.class, PlanarVelocityToApply.class);
	}
	
	@Override
	protected void onUpdated() {
		for(Entity impE : sets.get(0))
			for(Entity phE : sets.get(1)){
				PlanarStance impStance = impE.get(PlanarStance.class);
				PlanarStance phStance = phE.get(PlanarStance.class);
				
				Physic ph = phE.get(Physic.class);
				PhysicForce impulse = impE.get(PhysicForce.class);
				if(impulse.exceptions.contains(ph.stat.type))
					return;

				Point2D vector = phStance.coord.getSubtraction(impStance.coord);
				double distance = vector.getLength(); 
				if(distance < impulse.radius){
					double rate = 1-(distance/impulse.radius);
					double force = impulse.force*rate;
					Point2D velocity = Point2D.ORIGIN.getTranslation(vector.getAngle(), force);
					PlanarVelocityToApply v = phE.get(PlanarVelocityToApply.class);
					velocity = velocity.getAddition(v.vector);
					setComp(phE, new PlanarVelocityToApply(velocity));
				}
			}
	}

}
