package model.ES.processor.physic.collision;

import java.awt.Color;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.debug.VelocityView;
import model.ES.component.debug.VelocityViewing;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.Physic;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.Dragging;
import model.ES.component.motion.collision.Collision;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class CollisionResolutionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(Collision.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		Collision col = e.get(Collision.class);
		Entity A = entityData.getEntity(col.getA(), Physic.class, PlanarVelocityToApply.class);
		Entity B = entityData.getEntity(col.getB(), Physic.class, PlanarVelocityToApply.class);
		
		Physic phA = A.get(Physic.class);
		Physic phB = B.get(Physic.class);
		
		
		Point2D velA = phA.getVelocity(); 
		Point2D velB = phB.getVelocity();
		
		double massA = phA.getMass();
		double massB = phB.getMass();
		
		
		Point2D relative = velB.getSubtraction(velA);
		double velAlongNormal = relative.getDotProduct(col.getNormal());
		
		if(velAlongNormal <= 0){
			double epsilon = Math.min(phA.getRestitution(), phB.getRestitution());
			double impulseScale = -(epsilon)*velAlongNormal;
			impulseScale /= 1/massA + 1/massB;
			
			Point2D impulse = col.getNormal().getScaled(impulseScale);
			
			Point2D newVelA = velA.getAddition(impulse.getNegation().getMult(1/massA));
			Point2D newVelB = velB.getAddition(impulse.getMult(1/massB));
			
//			PlanarVelocityToApply toApplyA = entityData.getComponent(A.getId(), PlanarVelocityToApply.class);
//			if(toApplyA != null)
//				newVelA = newVelA.getAddition(toApplyA.getVector());
//			setComp(A, new PlanarVelocityToApply(newVelA));
			setComp(A, new Physic(newVelA, phA.getMass(), phA.getShape(), phA.getRestitution()));

//			PlanarVelocityToApply toApplyB = entityData.getComponent(B.getId(), PlanarVelocityToApply.class);
//			if(toApplyB != null)
//				newVelB = newVelB.getAddition(toApplyB.getVector());
//			setComp(B, new PlanarVelocityToApply(newVelB));
			setComp(B, new Physic(newVelB, phB.getMass(), phB.getShape(), phB.getRestitution()));

			// debug
			VelocityViewing viewing = entityData.getComponent(A.getId(), VelocityViewing.class);
			if(viewing != null)
				viewing.updateVelocity("impulse", newVelA, Color.red, 10, 0.2, 1);
		}
		entityData.removeEntity(e.getId());
	}
}


































