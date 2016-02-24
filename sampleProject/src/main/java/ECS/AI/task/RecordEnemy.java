package ECS.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import ECS.AI.blackboard.ShipBlackboard;

public class RecordEnemy extends LeafTask<ShipBlackboard>{
	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		bb.enemy = bb.enemyDetected;
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}
}
