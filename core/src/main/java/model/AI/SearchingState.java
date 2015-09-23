package model.AI;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
import model.ES.component.senses.Sighting;
import util.LogUtil;
import util.math.AngleUtil;
import util.math.RandomUtil;

public class SearchingState extends EntityStateActor {
	
	private long startTime = System.currentTimeMillis();
	private double angleToCheck;
	private double nextMoveTime = 0;
	
	
	public SearchingState(EntityData entityData, EntityId eid) {
		super(entityData, eid);
	}

	@Override
	protected void execute() {
		if(System.currentTimeMillis()-startTime > 5000){
			endExecution();
			LogUtil.info("stop searching");
			return;
		}
		Sighting s = entityData.getComponent(eid, Sighting.class);
		
		for(EntityId inSight : s.entitiesInSight){
			PlayerControl control = entityData.getComponent(inSight, PlayerControl.class);
			if(control != null){
				AttackState attack = new AttackState(entityData, eid, inSight);
				endExecution();
				pushState(attack);
				return;
			}
		}

		if(nextMoveTime < System.currentTimeMillis()){
			angleToCheck = RandomUtil.next()*AngleUtil.FULL;
			nextMoveTime = System.currentTimeMillis()+RandomUtil.between(900, 1500);
		}
		PlanarStance stance = entityData.getComponent(eid, PlanarStance.class);
		double neededRotAngle;
		int turn = AngleUtil.getTurn(stance.getOrientation(), angleToCheck);
		if(turn != AngleUtil.NONE){
			double diff = AngleUtil.getSmallestDifference(stance.getOrientation(), angleToCheck);
			if(turn >= 0)
				neededRotAngle = diff;
			else
				neededRotAngle = -diff;
			entityData.setComponent(eid, new PlanarNeededRotation(neededRotAngle));
		}

	}

}
