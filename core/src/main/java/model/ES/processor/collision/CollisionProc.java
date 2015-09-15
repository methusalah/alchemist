package model.ES.processor.collision;

import java.util.List;

import util.LogUtil;
import util.geometry.geom2d.Point2D;
import model.ModelManager;
import model.ES.component.collision.Collision;
import model.ES.component.collision.Physic;
import model.ES.component.planarMotion.PlanarStance;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class CollisionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(Physic.class, PlanarStance.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
		if(ModelManager.command.target == null)
			return;
		
        for(EntitySet set : sets)
        	for (Entity e1 : set)
            	for (Entity e2 : set){
            		if(e1 != e2)
            			checkCollisionBetween(e1, e2); 
            	}
	}

	private void checkCollisionBetween(Entity e, Entity other) {
		PlanarStance stance1 = e.get(PlanarStance.class);
		PlanarStance stance2 = other.get(PlanarStance.class);
		
		Physic ph1 = e.get(Physic.class);
		Physic ph2 = other.get(Physic.class);
		
		Point2D c1 = stance1.getCoord();
		Point2D c2 = stance2.getCoord();
		
		
		double d = c1.getDistance(c2);
		double spacing = ph1.getShape().radius+ph2.getShape().radius; 
		// collision test
		if(d <= spacing){
			// TODO make the collision test only once for each entity couple.
			Point2D v = c2.getSubtraction(c1);
			v = v.getScaled(ph1.getShape().radius-((spacing-d)/2));
			Point2D collisionPoint = c1.getAddition(v);
			
			Point2D normal = v.getNormalized().getNegation();
			List<Collision> collisions = ph1.getCurrentCollisions();
			collisions.add(new Collision(other.getId(), collisionPoint, normal));
			setComp(e, new Physic(ph1.getShape(), collisions));
			
			LogUtil.info("colissiooosiiosioiosioi");
		}
	}
}


































