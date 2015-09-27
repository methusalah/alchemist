package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.simsilica.es.EntityId;

import model.AI.AttackState;
import model.AI.blackboard.ShipBlackboard;
import model.ES.component.command.PlayerControl;
import model.ES.component.senses.Sighting;
import util.LogUtil;

public class EnemyInSight extends LeafTask<ShipBlackboard> {

	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		Sighting s = bb.entityData.getComponent(bb.eid, Sighting.class);
		
		for(EntityId inSight : s.entitiesInSight){
			PlayerControl control = bb.entityData.getComponent(inSight, PlayerControl.class);
			if(control != null){
				bb.enemy = inSight;
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
