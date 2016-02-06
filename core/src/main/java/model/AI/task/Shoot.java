package model.AI.task;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.ability.AbilityTrigger;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

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
