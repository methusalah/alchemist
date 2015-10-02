package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.Physic;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import util.math.RandomUtil;

public class Aim extends LeafTask<ShipBlackboard> {

	@Override
	public void start() {
	}
	
	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		PlanarStance stance = bb.entityData.getComponent(bb.eid, PlanarStance.class);
		PlanarStance enemyStance = bb.entityData.getComponent(bb.enemy, PlanarStance.class);
		Physic enemyPhysic = bb.entityData.getComponent(bb.enemy, Physic.class);
		
		Point2D toAim = enemyStance.coord.getAddition(enemyPhysic.velocity);
		
		double neededRotation = AngleUtil.getAngleFromAtoB(stance.orientation, toAim.getSubtraction(stance.coord).getAngle());
		if(neededRotation != 0)
			bb.entityData.setComponent(bb.eid, new PlanarNeededRotation(neededRotation));
		
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
