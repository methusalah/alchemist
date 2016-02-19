package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.motion.PlanarStance;

public class IsEnemyDead extends LeafTask<ShipBlackboard> {

	@Override
	public void run() {
		if(getObject().entityData.getComponent(getObject().enemy, PlanarStance.class) == null)
			success();
		else
			fail();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
