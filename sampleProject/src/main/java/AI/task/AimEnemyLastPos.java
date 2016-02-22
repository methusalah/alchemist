package AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import AI.blackboard.ShipBlackboard;
import component.motion.PlanarNeededRotation;
import component.motion.PlanarStance;
import component.motion.physic.Physic;
import util.geometry.geom2d.Point2D;
import util.math.Angle;
import util.math.AngleUtil;

public class AimEnemyLastPos extends LeafTask<ShipBlackboard> {

	@Override
	public void start() {
	}
	
	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		PlanarStance stance = bb.entityData.getComponent(bb.eid, PlanarStance.class);
		PlanarStance enemyStance = bb.entityData.getComponent(bb.enemy, PlanarStance.class);
		Physic enemyPhysic = bb.entityData.getComponent(bb.enemy, Physic.class);
		
		Point2D toAim = enemyStance.coord.getAddition(enemyPhysic.getVelocity());
		
		double neededRotation = AngleUtil.getAngleFromAtoB(stance.orientation.getValue(), toAim.getSubtraction(stance.coord).getAngle());
		if(neededRotation != 0)
			bb.entityData.setComponent(bb.eid, new PlanarNeededRotation(new Angle(neededRotation)));
		
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
