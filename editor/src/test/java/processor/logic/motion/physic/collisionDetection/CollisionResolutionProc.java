package test.java.processor.logic.motion.physic.collisionDetection;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import main.java.model.ECS.pipeline.Processor;
import model.ECS.Naming;
import model.ES.component.lifeCycle.ToRemove;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.Touching;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Physic;
import util.LogUtil;
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
	
	static void createCollisionBetween(EntityData entityData, Entity e1, Entity e2, double penetration, Point2D impactNormal, Point2D impactCoord) {
		Physic ph1 = e1.get(Physic.class);
		Physic ph2 = e2.get(Physic.class);
		// collision test
		if(penetration > 0){
			// spawner exception allows, for exemple, missiles to get out of a ship by ignoring collision. 
			if(ph1.getSpawnerException() != e2.getId() && ph2.getSpawnerException() != e1.getId()){
				// we create a new entity for managing the collision physics
				EntityId collision = entityData.createEntity();
				entityData.setComponent(collision, new Naming("collision"));
				entityData.setComponent(collision, new Collisioning(e1.getId(), e2.getId(), penetration, impactNormal));
				entityData.setComponent(collision, new ToRemove());
				
				// we add touching component to each entity for other effects than physics
				entityData.setComponent(e1.getId(), new Touching(e2.getId(), impactCoord, impactNormal));
				entityData.setComponent(e2.getId(), new Touching(e1.getId(), impactCoord, impactNormal.getNegation()));
			}
		} else {
			if(ph1.getSpawnerException() == e2.getId())
				entityData.setComponent(e1.getId(), new Physic(ph1.getVelocity(), ph1.getType(), ph1.getExceptions(), ph1.getMass(), ph1.getRestitution(), null));
			if(ph2.getSpawnerException() == e1.getId())
				entityData.setComponent(e2.getId(), new Physic(ph2.getVelocity(), ph2.getType(), ph2.getExceptions(), ph2.getMass(), ph2.getRestitution(), null));
		}
	}
}


































