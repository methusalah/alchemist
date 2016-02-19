package processor.logic.motion.physic;

import com.simsilica.es.Entity;

import model.ECS.pipeline.Processor;

public class PhysicForceProc extends Processor {

	@Override
	protected void registerSets() {
		register("force", PlanarStance.class, PhysicForce.class);
		register("influenced", PlanarStance.class, Physic.class, PlanarVelocityToApply.class);
	}
	
	@Override
	protected void onUpdated() {
		for(Entity impE : getSet("force"))
			for(Entity phE : getSet("influenced")){
				PlanarStance impStance = impE.get(PlanarStance.class);
				PlanarStance phStance = phE.get(PlanarStance.class);
				
				Physic ph = phE.get(Physic.class);
				PhysicForce impulse = impE.get(PhysicForce.class);
				if(impulse.exceptions.contains(ph.getType()))
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
