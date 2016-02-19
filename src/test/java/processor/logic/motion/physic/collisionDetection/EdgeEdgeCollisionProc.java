package processor.logic.motion.physic.collisionDetection;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.Entity;

import model.ECS.pipeline.Processor;

public class EdgeEdgeCollisionProc extends Processor {
	
	
	@Override
	protected void registerSets() {
		register("edge", Physic.class, PlanarStance.class, EdgedCollisionShape.class);
	}
	
	@Override
	protected void onUpdated() {
    	List<Entity> entities = new ArrayList<>();
    	for(Entity e : getSet("edge")){
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
		if(ph1.getExceptions().contains(ph2.getType()) || ph2.getExceptions().contains(ph1.getType()))
			return;

		EdgedCollisionShape edgeShape1 = e1.get(EdgedCollisionShape.class);
		EdgedCollisionShape edgeShape2 = e2.get(EdgedCollisionShape.class);

		Point2D impactCoord = null, impactNormal = null;
		double penetration = 0;
		
		// detection

		if(penetration != 0)
			CollisionResolutionProc.createCollisionBetween(entityData, e1, e2, penetration, impactNormal, impactCoord);
	}
}


































