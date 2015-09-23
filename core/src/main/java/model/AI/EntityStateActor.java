package model.AI;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import util.FSM.StateActor;

public abstract class EntityStateActor extends StateActor {

	protected final EntityData entityData;
	protected final EntityId eid;
	
	public EntityStateActor(EntityData entityData, EntityId eid){
		this.entityData = entityData;
		this.eid = eid;
	}
}
