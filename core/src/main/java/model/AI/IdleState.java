package model.AI;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.component.command.PlayerControl;
import model.ES.component.senses.Sighting;

public class IdleState extends EntityStateActor {
	
	public IdleState(EntityData entityData, EntityId eid) {
		super(entityData, eid);
	}

	@Override
	protected void execute(){
		Sighting s = entityData.getComponent(eid, Sighting.class);
		
		for(EntityId inSight : s.entitiesInSight){
			PlayerControl control = entityData.getComponent(inSight, PlayerControl.class);
			if(control != null){
				AttackState attack = new AttackState(entityData, eid, inSight);
				pushState(attack);
				break;
			}
		}
	}

}
