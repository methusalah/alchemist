package model.AI.task;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.combat.resistance.Attrition;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;

public class IsLowLife extends LeafTask<ShipBlackboard> {

	@TaskAttribute
	public int dist = 3;
	
	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		Attrition attrition = bb.entityData.getComponent(bb.eid, Attrition.class);
		if((double)attrition.getActualHitpoints() / attrition.getMaxHitpoints() < 0.2){
			success();
		} else {
			fail();
		}
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
