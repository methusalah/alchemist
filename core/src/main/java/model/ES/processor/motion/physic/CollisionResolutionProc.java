package model.ES.processor.motion.physic;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.ToRemove;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Physic;
import util.geometry.geom2d.Point2D;

public class CollisionResolutionProc extends Processor {
	
	@Override
	protected void registerSets() {
		registerDefault(Collisioning.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		Collisioning col = e.get(Collisioning.class);
		Entity A = entityData.getEntity(col.a, Physic.class, PlanarVelocityToApply.class);
		Entity B = entityData.getEntity(col.b, Physic.class, PlanarVelocityToApply.class);
		
		Physic phA = A.get(Physic.class);
		Physic phB = B.get(Physic.class);
		
		Point2D velA = phA.getVelocity(); 
		Point2D velB = phB.getVelocity();
		
		double massA = phA.getMass();
		double massB = phB.getMass();
		
		Point2D relative = velB.getSubtraction(velA);
		double velAlongNormal = relative.getDotProduct(col.normal);
		
		if(velAlongNormal <= 0){
			double epsilon = Math.min(phA.getRestitution().getValue(), phB.getRestitution().getValue());
			double impulseScale = -(1+epsilon)*velAlongNormal;
			impulseScale /= 1/massA + 1/massB;
			
			Point2D impulse = col.normal.getScaled(impulseScale);
			
			Point2D newVelA = velA.getAddition(impulse.getNegation().getMult(1/massA));
			Point2D newVelB = velB.getAddition(impulse.getMult(1/massB));
			
			setComp(A, new Physic(newVelA, phA.getType(), phA.getExceptions(), phA.getMass(), phA.getRestitution(), phA.getSpawnerException()));
			setComp(B, new Physic(newVelB, phB.getType(), phB.getExceptions(), phB.getMass(), phB.getRestitution(), phB.getSpawnerException()));
		}
		setComp(e, new ToRemove());
	}
}


































