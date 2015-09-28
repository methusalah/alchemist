package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.interaction.senses.Touching;
import model.ES.component.relation.Attackable;

public class IsEnemyTouching extends LeafTask<ShipBlackboard> {

	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		Touching touching = bb.entityData.getComponent(bb.eid, Touching.class);
		
		if(touching != null)
			if(bb.entityData.getComponent(touching.getTouched(), Attackable.class) != null){
				bb.enemyDetected = touching.getTouched();
				success();
			}
		else
			fail();

	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
