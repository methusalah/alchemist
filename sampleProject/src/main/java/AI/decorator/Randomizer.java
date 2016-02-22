package AI.decorator;

import com.badlogic.gdx.ai.btree.Decorator;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;

import AI.blackboard.ShipBlackboard;
import util.math.RandomUtil;

public class Randomizer extends Decorator<ShipBlackboard> {
	@TaskAttribute
	public double prob = 0.5;

	@Override
	public void start() {
		if(RandomUtil.next() < prob)
			super.start();
		else
			fail();
	}
}
