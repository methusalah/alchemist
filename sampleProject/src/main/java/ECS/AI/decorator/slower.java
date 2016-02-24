package ECS.AI.decorator;

import com.badlogic.gdx.ai.btree.Decorator;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;

import ECS.AI.blackboard.ShipBlackboard;
import util.math.RandomUtil;

public class slower extends Decorator<ShipBlackboard> {
	private static final String CHRONO = "chrono";
	private static final String DURATION = "duration";
	
	@TaskAttribute
	public int duration = 200;

	@TaskAttribute
	public int range = 0;
	
	@Override
	public void start() {
		getObject().data.put(CHRONO, System.currentTimeMillis());
		getObject().data.put(DURATION, range == 0? duration : duration + range*RandomUtil.next());
	}

	@Override
	public void run() {
		if((long)getObject().data.get(CHRONO) + (double)getObject().data.get(DURATION) > System.currentTimeMillis()){
			running();
		} else {
			super.start();
			super.run();
		}
	}
}
