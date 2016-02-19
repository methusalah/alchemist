package AI.decorator;

import com.badlogic.gdx.ai.btree.Decorator;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;

import AI.blackboard.ShipBlackboard;
import util.LogUtil;
import util.math.RandomUtil;

public class Succeeder extends Decorator<ShipBlackboard> {
	@Override
	public void childFail(Task<ShipBlackboard> runningTask) {
		success();
	}
}
