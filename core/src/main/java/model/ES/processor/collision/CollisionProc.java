package model.ES.processor.collision;

import java.util.ArrayList;
import java.util.List;

import util.LogUtil;
import util.geometry.geom2d.Point2D;
import model.ModelManager;
import model.ES.component.collision.Collision;
import model.ES.component.collision.Physic;
import model.ES.component.planarMotion.PlanarStance;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
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
		
        for(EntitySet set : sets){
        	List<Entity> entities = new ArrayList<>();
        	for(Entity e : set)
        		entities.add(e);
        	for(int i = 0; i < set.size() - 1; i++)
            	for(int j = i+1; j < set.size(); j++)
        			checkCollisionBetween(entities.get(i), entities.get(j));
        }
	}

	private void checkCollisionBetween(Entity e1, Entity e2) {
		PlanarStance stance1 = e1.get(PlanarStance.class);
		PlanarStance stance2 = e2.get(PlanarStance.class);
		
		Physic ph1 = e1.get(Physic.class);
		Physic ph2 = e2.get(Physic.class);
		
		Point2D c1 = stance1.getCoord();
		Point2D c2 = stance2.getCoord();
		
		
		double d = c1.getDistance(c2);
		double spacing = ph1.getShape().radius+ph2.getShape().radius; 
		// collision test
		if(d <= spacing){
			Point2D v = c2.getSubtraction(c1);
			v = v.getScaled(ph1.getShape().radius-((spacing-d)/2));
			Point2D collisionPoint = c1.getAddition(v);
			
			Point2D normal = v.getNormalized();
			EntityId collision = entityData.createEntity();
			entityData.setComponent(collision, new Collision(e1.getId(), e2.getId(), collisionPoint, normal));
		}
	}
}


































