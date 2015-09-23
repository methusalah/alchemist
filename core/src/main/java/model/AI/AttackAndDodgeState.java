package model.AI;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.motion.PlanarStance;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

public class AttackAndDodgeState extends EntityStateActor {
	private final EntityId target;
	
	public AttackAndDodgeState(EntityData entityData, EntityId eid, EntityId target) {
		super(entityData, eid);
		this.target = target;
	}

	@Override
	protected void execute() {
		PlanarStance stance = entityData.getComponent(eid, PlanarStance.class);

		PlanarStance ennemyStance = entityData.getComponent(target, PlanarStance.class);
		
		Point2D vector = ennemyStance.getCoord().getSubtraction(stance.getCoord());
		
		// aim target
		double neededAngle = vector.getAngle();
		double neededRotAngle;
		int turn = AngleUtil.getTurn(stance.getOrientation(), neededAngle);
		if(turn != AngleUtil.NONE){
			double diff = AngleUtil.getSmallestDifference(stance.getOrientation(), neededAngle);
			if(turn >= 0)
				neededRotAngle = diff;
			else
				neededRotAngle = -diff;
			entityData.setComponent(eid, new PlanarNeededRotation(neededRotAngle));
		}
		entityData.setComponent(eid, new PlanarNeededThrust(vector.getNormalized().getRotation(AngleUtil.RIGHT)));
		if(vector.getLength() > 9 || vector.getLength() < 5)
			endExecution();
	}

}
