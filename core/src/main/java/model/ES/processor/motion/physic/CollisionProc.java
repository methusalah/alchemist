package model.ES.processor.motion.physic;

import java.util.ArrayList;
import java.util.List;

import util.LogUtil;
import util.geometry.geom2d.Point2D;
import model.ModelManager;
import model.ES.component.Naming;
import model.ES.component.ToRemove;
import model.ES.component.interaction.senses.Touching;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.CircleCollisionShape;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Physic;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.ECS.Processor;

public class CollisionProc extends Processor {
	
	@Override
	protected void registerSets() {
		registerDefault(Physic.class, PlanarStance.class, CircleCollisionShape.class);
	}
	
	@Override
	protected void onUpdated() {
    	List<Entity> entities = new ArrayList<>();
    	for(Entity e : getDefaultSet()){
    		entities.add(e);
    		removeComp(e, Touching.class);
    	}
    	for(int i = 0; i < getDefaultSet().size() - 1; i++){
        	for(int j = i+1; j < getDefaultSet().size(); j++)
    			checkCollisionBetween(entities.get(i), entities.get(j));
    	}
	}

	private void checkCollisionBetween(Entity e1, Entity e2) {
		PlanarStance stance1 = e1.get(PlanarStance.class);
		PlanarStance stance2 = e2.get(PlanarStance.class);
		
		Physic ph1 = e1.get(Physic.class);
		Physic ph2 = e2.get(Physic.class);

		CircleCollisionShape shape1 = e1.get(CircleCollisionShape.class);
		CircleCollisionShape shape2 = e2.get(CircleCollisionShape.class);

		if(ph1.getExceptions().contains(ph2.getType()) || ph2.getExceptions().contains(ph1.getType()))
			return;
		
		Point2D c1 = stance1.coord;
		Point2D c2 = stance2.coord;

		double d = c1.getDistance(c2);
		double spacing = shape1.getRadius()+shape2.getRadius();
		double penetration = spacing-d;
		// collision test
		if(penetration > 0){
			// spawner exception allows, for exemple, missiles to get out of a ship by ignoring collision. 
			if(ph1.getSpawnerException() != e2.getId() && ph2.getSpawnerException() != e1.getId()){
				Point2D v = c2.getSubtraction(c1);
				v = v.getScaled(shape1.getRadius()-((spacing-d)/2));
				
				Point2D normal = c2.getSubtraction(c1).getNormalized();
				
				// we create a new entity for managing the collision physics
				EntityId collision = entityData.createEntity();
				entityData.setComponent(collision, new Naming("collision"));
				entityData.setComponent(collision, new Collisioning(e1.getId(), e2.getId(), spacing-d, normal));
				entityData.setComponent(collision, new ToRemove());
				
				
				// we add touching component to each entity for other effects than physics
				Point2D impactCoord = c1.getAddition(normal.getScaled(shape1.getRadius()));
				setComp(e1, new Touching(e2.getId(), impactCoord, normal));
	
				normal = normal.getNegation();
				impactCoord = c2.getAddition(normal.getScaled(shape2.getRadius()));
				setComp(e2, new Touching(e1.getId(), impactCoord, normal));
			}
		} else {
			if(ph1.getSpawnerException() == e2.getId())
				setComp(e1, new Physic(ph1.getVelocity(), ph1.getType(), ph1.getExceptions(), ph1.getMass(), ph1.getRestitution(), null));
			if(ph2.getSpawnerException() == e1.getId())
				setComp(e2, new Physic(ph2.getVelocity(), ph2.getType(), ph2.getExceptions(), ph2.getMass(), ph2.getRestitution(), null));
		}
	}
}


































