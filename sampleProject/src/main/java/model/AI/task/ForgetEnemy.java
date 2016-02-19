package model.AI.task;

import util.LogUtil;
import model.AI.blackboard.ShipBlackboard;
import model.ES.component.motion.PlanarStance;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

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
