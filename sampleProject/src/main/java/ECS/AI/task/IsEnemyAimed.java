package ECS.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import ECS.AI.blackboard.ShipBlackboard;

public class IsEnemyAimed extends LeafTask<ShipBlackboard> {
	
	@Override
	public void run() {
		ShipBlackboard bb = getObject();

		if(bb.enemyShootable)
			success();
		else
			fail();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}
}
