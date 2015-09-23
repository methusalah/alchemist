package model.AI;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
import model.ES.component.senses.Sighting;

public class AttackState extends EntityStateActor {
	private final EntityId target;
	
	public AttackState(EntityData entityData, EntityId eid, EntityId target) {
		super(entityData, eid);
		this.target = target;
	}

	@Override
	protected void execute() {
		Sighting s = entityData.getComponent(eid, Sighting.class);

		boolean hasInSight = false;
		for(EntityId inSight : s.entitiesInSight){
			PlayerControl control = entityData.getComponent(inSight, PlayerControl.class);
			if(control != null){
				hasInSight = true;
				break;
			}
		}
		if(!hasInSight){
			endExecution();
			pushState(new SearchingState(entityData, eid));
			return;
		}

		PlanarStance stance = entityData.getComponent(eid, PlanarStance.class);

		PlanarStance ennemyStance = entityData.getComponent(target, PlanarStance.class);
		
		double distance = ennemyStance.getCoord().getDistance(stance.getCoord());
		if(distance > 12)
			pushState(new PursueState(entityData, eid, target));
		else if(distance > 8)
			pushState(new AttackAndApproachState(entityData, eid, target));
		else if(distance > 5)
			pushState(new AttackAndDodgeState(entityData, eid, target));
		else if(distance > 2)
			pushState(new AttackAndStepBackState(entityData, eid, target));
		else
			pushState(new StepBackState(entityData, eid, target));
	}

}
