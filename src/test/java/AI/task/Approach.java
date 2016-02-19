package AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import AI.blackboard.ShipBlackboard;
import component.motion.PlanarNeededThrust;

public class Approach extends LeafTask<ShipBlackboard> {
	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		bb.entityData.setComponent(bb.eid, new PlanarNeededThrust(bb.getVectorToEnemy()));
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
