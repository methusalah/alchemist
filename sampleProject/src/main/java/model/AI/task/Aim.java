package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.sun.org.apache.bcel.internal.generic.IFNE;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.motion.PlanarNeededRotation;
import model.ES.component.motion.PlanarNeededThrust;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.Physic;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.Angle;
import util.math.AngleUtil;
import util.math.RandomUtil;

public class Aim extends LeafTask<ShipBlackboard> {

	@TaskAttribute
	public double accuracy = 0.9;

	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		PlanarStance stance = bb.entityData.getComponent(bb.eid, PlanarStance.class);
		PlanarStance enemyStance = bb.entityData.getComponent(bb.enemy, PlanarStance.class);
		Physic enemyPhysic = bb.entityData.getComponent(bb.enemy, Physic.class);
		
		Point2D toAim = enemyStance.coord.getAddition(enemyPhysic.getVelocity());
		
		double neededRotation = AngleUtil.getAngleFromAtoB(stance.orientation.getValue(), toAim.getSubtraction(stance.coord).getAngle());
		
		bb.enemyShootable = neededRotation < AngleUtil.FLAT*(1-accuracy);
			
		if(neededRotation != 0)
			bb.entityData.setComponent(bb.eid, new PlanarNeededRotation(new Angle(neededRotation)));
		
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
