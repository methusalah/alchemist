package model.AI.blackboard;

import java.util.HashMap;
import java.util.Map;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.component.motion.PlanarStance;
import util.geometry.geom2d.Point2D;

public class ShipBlackboard {
	
	public Map<String, Object> data = new HashMap<>();
	public final EntityData entityData;
	public final EntityId eid;
	
	public EntityId enemyDetected;
	public EntityId enemy;
	
	public ShipBlackboard(EntityData entityData, EntityId eid) {
		this.entityData = entityData;
		this.eid = eid;
	}
	
	public Point2D getVectorToEnemy(){
		if(enemy == null)
			throw new RuntimeException("enemy not set.");
		PlanarStance stance = entityData.getComponent(eid, PlanarStance.class);
		PlanarStance enemyStance = entityData.getComponent(enemy, PlanarStance.class);
		return enemyStance.coord.getSubtraction(stance.coord);
	}
	
	public double getDistanceToEnemy(){
		if(enemy == null)
			throw new RuntimeException("enemy not set.");
		PlanarStance stance = entityData.getComponent(eid, PlanarStance.class);
		PlanarStance enemyStance = entityData.getComponent(enemy, PlanarStance.class);
		return enemyStance.coord.getDistance(stance.coord);
	}
}
