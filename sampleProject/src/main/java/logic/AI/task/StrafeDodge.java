package logic.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import component.motion.PlanarNeededThrust;
import logic.AI.blackboard.ShipBlackboard;
import util.math.AngleUtil;
import util.math.RandomUtil;

public class StrafeDodge extends LeafTask<ShipBlackboard> {
	private static final String DODGE_ANGLE = "dodgeAngle";
	private static final String DODGE_CHRONO = "dodgeChrono";

	@Override
	public void start() {
		if(!getObject().data.containsKey(DODGE_CHRONO) || (long)getObject().data.get(DODGE_CHRONO)+500 < System.currentTimeMillis()){
			getObject().data.remove(DODGE_ANGLE);
			getObject().data.put(DODGE_CHRONO, System.currentTimeMillis());
		}
	}

	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		if(!bb.data.containsKey(DODGE_ANGLE)){
			int direction = RandomUtil.next() > 0.5?1:-1; 
			bb.data.put(DODGE_ANGLE, AngleUtil.RIGHT*RandomUtil.between(0.8,  1.2)*direction);
		}
		bb.entityData.setComponent(bb.eid, new PlanarNeededThrust(bb.getVectorToEnemy().getRotation((double)bb.data.get(DODGE_ANGLE))));
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}
	
}
