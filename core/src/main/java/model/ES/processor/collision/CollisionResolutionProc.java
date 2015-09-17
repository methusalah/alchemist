package model.ES.processor.collision;

import java.awt.Color;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.debug.VelocityView;
import model.ES.component.debug.VelocityViewing;
import model.ES.component.physic.Physic;
import model.ES.component.physic.collision.Collision;
import model.ES.component.planarMotion.PlanarMotionCapacity;
import model.ES.component.planarMotion.PlanarWipping;
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
		Entity A = entityData.getEntity(col.getA(), PlanarWipping.class, PlanarMotionCapacity.class, Physic.class);
		Entity B = entityData.getEntity(col.getB(), PlanarWipping.class, PlanarMotionCapacity.class, Physic.class);
		
		Physic phA = A.get(Physic.class);
		Physic phB = B.get(Physic.class);
		
		
		Point2D velA = A.get(PlanarWipping.class).getVelocity(); 
		Point2D velB = B.get(PlanarWipping.class).getVelocity();
		
		double massA = A.get(PlanarMotionCapacity.class).getMass();
		double massB = B.get(PlanarMotionCapacity.class).getMass();
		
		
		Point2D relative = velB.getSubtraction(velA);
		double velAlongNormal = relative.getDotProduct(col.getNormal());
		
		if(velAlongNormal <= 0){
			double epsilon = Math.min(phA.getRestitution(), phB.getRestitution());
			double impulseScale = -(epsilon)*velAlongNormal;
			impulseScale /= 1/massA + 1/massB;
			
			Point2D impulse = col.getNormal().getScaled(impulseScale);
			
			Point2D newVelA = velA.getAddition(impulse.getNegation().getMult(1/massA));
			Point2D newVelB = velB.getAddition(impulse.getMult(1/massB));
			
			setComp(A, new PlanarWipping(newVelA, A.get(PlanarWipping.class).getDragging()));
			setComp(B, new PlanarWipping(newVelB, B.get(PlanarWipping.class).getDragging()));

			// debug
			VelocityViewing viewing = entityData.getComponent(A.getId(), VelocityViewing.class);
			if(viewing != null)
				viewing.updateVelocity("impulse", newVelA, Color.red, 10, 0.2, 1);
		}
		entityData.removeEntity(e.getId());
	}
}


































