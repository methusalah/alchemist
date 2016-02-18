package model.ES.processor.motion.physic.collisionDetection;

import java.util.ArrayList;
import java.util.List;

import model.ECS.Naming;
import model.ES.component.lifeCycle.ToRemove;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.Touching;
import model.ES.component.motion.physic.CircleCollisionShape;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.EdgedCollisionShape;
import model.ES.component.motion.physic.Physic;
import util.LogUtil;
import util.geometry.geom2d.BoundingCircle;
import util.geometry.geom2d.Circle2D;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Segment2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import main.java.model.ECS.pipeline.Processor;

public class CircleCircleCollisionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register("circle", Physic.class, PlanarStance.class, CircleCollisionShape.class);
	}
	
	@Override
	protected void onUpdated() {
    	List<Entity> entities = new ArrayList<>();
    	for(Entity e : getSet("circle")){
    		entities.add(e);
    	}
    	for(int i = 0; i < entities.size() - 1; i++){
        	for(int j = i+1; j < entities.size(); j++){
        		recordCollisionState(entities.get(i), entities.get(j));
        	}
    	}
	}

	private void recordCollisionState(Entity e1, Entity e2) {
		PlanarStance stance1 = e1.get(PlanarStance.class);
		PlanarStance stance2 = e2.get(PlanarStance.class);
		
		Physic ph1 = e1.get(Physic.class);
		Physic ph2 = e2.get(Physic.class);
		if(!ph1.getType().isEmpty() && ph2.getExceptions().contains(ph1.getType()) || 
				!ph2.getType().isEmpty() && ph1.getExceptions().contains(ph2.getType())){
			return;
		}

		Circle2D c1 = new Circle2D(stance1.getCoord(), e1.get(CircleCollisionShape.class).getRadius());
		Circle2D c2 = new Circle2D(stance2.getCoord(), e2.get(CircleCollisionShape.class).getRadius());

		
		Point2D impactCoord = null, impactNormal = null;
		double penetration = 0;
		double d = c1.center.getDistance(c2.center);
		double spacing = c1.radius+c2.radius;
		penetration = Math.max(0, spacing-d);
		impactNormal = c2.center.getSubtraction(c1.center).getNormalized();
		impactCoord = c1.center.getAddition(impactNormal.getScaled(c1.radius));
//		if(penetration >0 )
//			LogUtil.info(entityData.getComponent(e1.getId(), Naming.class).getName() +
//					" collides with "+
//						entityData.getComponent(e2.getId(), Naming.class).getName());
		
		CollisionResolutionProc.createCollisionBetween(entityData, e1, e2, penetration, impactNormal, impactCoord);
	}
}


































