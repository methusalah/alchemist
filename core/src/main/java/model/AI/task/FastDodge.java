package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.motion.PlanarStance;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import util.math.RandomUtil;

public class FastDodge extends LeafTask<ShipBlackboard> {
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
		PlanarStance stance = bb.entityData.getComponent(bb.eid, PlanarStance.class);

		if(!bb.data.containsKey(DODGE_ANGLE)){
			int direction = RandomUtil.next() > 0.5?1:-1; 
			bb.data.put(DODGE_ANGLE, AngleUtil.RIGHT*RandomUtil.between(0.8,  1.2)*direction);
		}
		double neededRotation = AngleUtil.getAngleFromAtoB(stance.orientation.getValue(), (double)bb.data.get(DODGE_ANGLE));
		if(neededRotation != 0)
			bb.entityData.setComponent(bb.eid, new PlanarNeededRotation(neededRotation));

		bb.entityData.setComponent(bb.eid, new PlanarNeededThrust(Point2D.UNIT_X.getRotation(stance.orientation.getValue())));
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}
	
}
