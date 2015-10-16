package model.AI.task;

import model.AI.blackboard.ShipBlackboard;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

public class IsEnemyAimed extends LeafTask<ShipBlackboard> {
	
	@Override
	public void run() {
		ShipBlackboard bb = getObject();

		if(bb.enemyIsInMyRedDot){
			success();
			return;
		}
		fail();

	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}
}
