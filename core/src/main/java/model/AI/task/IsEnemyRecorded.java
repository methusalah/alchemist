package model.AI.task;

import util.LogUtil;
import model.AI.blackboard.ShipBlackboard;
import model.ES.component.ability.Sighting;
import model.ES.component.combat.Attackable;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.simsilica.es.EntityId;

public class IsEnemyRecorded extends LeafTask<ShipBlackboard> {

	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		if(bb.enemy == null){
			fail();
		} else {
			success();
		}
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
