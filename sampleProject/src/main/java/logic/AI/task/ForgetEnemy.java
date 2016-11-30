package logic.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import logic.AI.blackboard.ShipBlackboard;
import util.LogUtil;

public class ForgetEnemy extends LeafTask<ShipBlackboard>{
	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		bb.enemy = null;
		LogUtil.info("forgot");

		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}
}
