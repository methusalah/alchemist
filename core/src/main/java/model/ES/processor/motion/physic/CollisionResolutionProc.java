package model.ES.processor.motion.physic;

import java.awt.Color;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.debug.VelocityViewing;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import model.ES.richData.VelocityView;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class CollisionResolutionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(Collisioning.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		Collisioning col = e.get(Collisioning.class);
		Entity A = entityData.getEntity(col.getA(), Physic.class, PlanarVelocityToApply.class);
		Entity B = entityData.getEntity(col.getB(), Physic.class, PlanarVelocityToApply.class);
		
		Physic phA = A.get(Physic.class);
		Physic phB = B.get(Physic.class);
		
		
		Point2D velA = phA.velocity; 
		Point2D velB = phB.velocity;
		
		double massA = phA.stat.mass;
		double massB = phB.stat.mass;
		
		
		Point2D relative = velB.getSubtraction(velA);
		double velAlongNormal = relative.getDotProduct(col.getNormal());
		
		if(velAlongNormal <= 0){
			double epsilon = Math.min(phA.stat.restitution, phB.stat.restitution);
			double impulseScale = -(1+epsilon)*velAlongNormal;
			impulseScale /= 1/massA + 1/massB;
			
			Point2D impulse = col.getNormal().getScaled(impulseScale);
			
			Point2D newVelA = velA.getAddition(impulse.getNegation().getMult(1/massA));
			Point2D newVelB = velB.getAddition(impulse.getMult(1/massB));
			
			setComp(A, new Physic(newVelA, phA.stat, phA.spawnerException));
			setComp(B, new Physic(newVelB, phB.stat, phB.spawnerException));

			// debug
//			VelocityViewing viewing = entityData.getComponent(A.getId(), VelocityViewing.class);
//			if(viewing != null)
//				viewing.updateVelocity("impulse", newVelA, Color.red, 10, 0.2, 1);
		}
		entityData.removeEntity(e.getId());
	}
}


































