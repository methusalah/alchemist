package model.AI.task;

import java.util.Map;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.simsilica.es.EntityId;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.relation.AbilityLinks;
import model.ES.component.shipGear.Trigger;

public class Shoot extends LeafTask<ShipBlackboard> {

	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		Map<String, EntityId> abilities = bb.entityData.getComponent(bb.eid, AbilityLinks.class).entities;
		for(String abilityName : abilities.keySet())
			if(abilityName.equals("gun"))
				bb.entityData.setComponent(abilities.get(abilityName), new Trigger(bb.eid, false));
			
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
