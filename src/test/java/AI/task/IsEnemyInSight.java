package AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.simsilica.es.EntityId;

import AI.blackboard.ShipBlackboard;
import component.ability.Sighting;
import component.combat.Attackable;

public class IsEnemyInSight extends LeafTask<ShipBlackboard> {

	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		Sighting s = bb.entityData.getComponent(bb.eid, Sighting.class);
		
		for(EntityId inSight : s.entitiesInSight){
			Attackable attackable = bb.entityData.getComponent(inSight, Attackable.class);
			if(attackable != null){
				bb.enemyDetected = inSight;
				success();
				return;
			}
		}
		fail();

	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}