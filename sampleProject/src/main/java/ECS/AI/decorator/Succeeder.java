package ECS.AI.decorator;

import com.badlogic.gdx.ai.btree.Decorator;
import com.badlogic.gdx.ai.btree.Task;

import ECS.AI.blackboard.ShipBlackboard;

public class Succeeder extends Decorator<ShipBlackboard> {
	@Override
	public void childFail(Task<ShipBlackboard> runningTask) {
		success();
	}
}
