package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.motion.PlanarStance;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import util.math.RandomUtil;

public class Flee extends LeafTask<ShipBlackboard> {
	private static final String EVASION_ANGLE = "evasionAngle";	
	private static final String EVASION_CHRONO = "evasionChrono";	

	@Override
	public void start() {
		if(!getObject().data.containsKey(EVASION_CHRONO) || (long)getObject().data.get(EVASION_CHRONO)+500 < System.currentTimeMillis()){
			getObject().data.remove(EVASION_ANGLE);
			getObject().data.put(EVASION_CHRONO, System.currentTimeMillis());
		}
	}
	
	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		PlanarStance stance = bb.entityData.getComponent(bb.eid, PlanarStance.class);
		
		// decide evasion angle
		if(!bb.data.containsKey(EVASION_ANGLE)){
			double evasionAngle;
			Point2D vector = bb.getVectorToEnemy();
			// if enemy is in front of agent
			if(AngleUtil.getSmallestDifference(stance.getOrientation(), vector.getAngle()) < AngleUtil.toRadians(45))
				evasionAngle = stance.getOrientation() - (AngleUtil.FLAT*RandomUtil.between(0.3, 1.3));
			else
				evasionAngle = stance.getOrientation();
			bb.data.put(EVASION_ANGLE, evasionAngle);
		}
		
		double evasionAngle = (double)bb.data.get(EVASION_ANGLE);
		double neededRotation = AngleUtil.getAngleFromAtoB(stance.getOrientation(), evasionAngle);
		if(neededRotation != 0)
			bb.entityData.setComponent(bb.eid, new PlanarNeededRotation(neededRotation));
		bb.entityData.setComponent(bb.eid, new PlanarNeededThrust(Point2D.UNIT_X.getRotation(stance.getOrientation())));

		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
