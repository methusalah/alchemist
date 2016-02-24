package ECS.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import ECS.AI.blackboard.ShipBlackboard;
import ECS.component.ability.AbilityTrigger;

public class Shoot extends LeafTask<ShipBlackboard> {

	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		bb.entityData.getComponent(bb.eid, AbilityTrigger.class).triggers.put("gun", true);
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
