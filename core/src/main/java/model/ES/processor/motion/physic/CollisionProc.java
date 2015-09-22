package model.ES.processor.motion.physic;

import java.util.ArrayList;
import java.util.List;

import util.LogUtil;
import util.geometry.geom2d.Point2D;
import model.ModelManager;
import model.ES.component.interaction.Touching;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Physic;

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

		if(ph1.stat.exceptions.contains(ph2.stat.type) || ph2.stat.exceptions.contains(ph1.stat.type))
			return;
		
		Point2D c1 = stance1.getCoord();
		Point2D c2 = stance2.getCoord();

		double d = c1.getDistance(c2);
		double spacing = ph1.stat.shape.radius+ph2.stat.shape.radius;
		double penetration = spacing-d;
		// collision test
		if(penetration > 0){
			// spawner exception allows, for exemple, missiles to get out of a ship by ignoring collision. 
			if(ph1.spawnerException != e2.getId() && ph2.spawnerException != e1.getId()){
				Point2D v = c2.getSubtraction(c1);
				v = v.getScaled(ph1.stat.shape.radius-((spacing-d)/2));
				
				Point2D normal = c2.getSubtraction(c1).getNormalized();
				
				// we create a new entity for managing the collision physics
				EntityId collision = entityData.createEntity();
				entityData.setComponent(collision, new Collisioning(e1.getId(), e2.getId(), spacing-d, normal));
				
				// we add touching component to each entity for other effects than physics
				Point2D impactCoord = c1.getAddition(normal.getScaled(ph1.stat.shape.radius));
				setComp(e1, new Touching(e2.getId(), impactCoord, normal));
	
				normal = normal.getNegation();
				impactCoord = c2.getAddition(normal.getScaled(ph2.stat.shape.radius));
				setComp(e2, new Touching(e1.getId(), impactCoord, normal));
			}
		} else {
			if(ph1.spawnerException == e2.getId())
				setComp(e1, new Physic(ph1.velocity, ph1.stat, null));
			if(ph2.spawnerException == e1.getId())
				setComp(e2, new Physic(ph2.velocity, ph2.stat, null));
		}
	}
}


































