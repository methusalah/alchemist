package AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;

import AI.blackboard.ShipBlackboard;

public class IsTooClose extends LeafTask<ShipBlackboard> {

	@TaskAttribute
	public int dist = 3;
	
	@Override
	public void run() {
		if(getObject().getDistanceToEnemy() < dist){
			success();
		} else {
			fail();
		}
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
