package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.simsilica.es.EntityId;

import model.AI.AttackState;
import model.AI.blackboard.ShipBlackboard;
import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
import model.ES.component.senses.Sighting;
import util.LogUtil;

public class TooFar extends LeafTask<ShipBlackboard> {

	@TaskAttribute
	public int dist = 3;
	
	@Override
	public void run() {
		if(getObject().getDistanceToEnemy() > dist){
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
