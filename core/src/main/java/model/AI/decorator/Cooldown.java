package model.AI.decorator;

import com.badlogic.gdx.ai.btree.Decorator;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;

import model.AI.blackboard.ShipBlackboard;
import util.LogUtil;
import util.math.RandomUtil;

public class Cooldown extends Decorator<ShipBlackboard> {
	private static final String COOLDOWN_CHRONO = "cooldownChrono";
	private static final String COOLDOWN_DURATION = "cooldownDuration";
	
	@TaskAttribute
	public int duration = 200;

	@TaskAttribute
	public int range = 0;
	
	@Override
	public void start() {
	}
	
	@Override
	public void run() {
		if(getObject().data.containsKey(COOLDOWN_CHRONO) && (long)getObject().data.get(COOLDOWN_CHRONO) + (double)getObject().data.get(COOLDOWN_DURATION) > System.currentTimeMillis())
			fail();
		else{
			getObject().data.remove(COOLDOWN_CHRONO);
			super.start();
			super.run();
		}
	}
	
	@Override
	public void childSuccess(Task<ShipBlackboard> runningTask) {
		super.childSuccess(runningTask);
		initChrono();
	}

	@Override
	public void childFail(Task<ShipBlackboard> runningTask) {
		super.childFail(runningTask);
		initChrono();
	}
	
	private void initChrono(){
		getObject().data.put(COOLDOWN_CHRONO, System.currentTimeMillis());
		getObject().data.put(COOLDOWN_DURATION, range == 0? duration : duration + range*RandomUtil.next());
	}
}
