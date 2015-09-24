package util.behaviorTree.genericDecorator;

import util.behaviorTree.Blackboard;
import util.behaviorTree.Task;
import util.behaviorTree.TaskDecorator;
import util.math.RandomUtil;

public class RandomDecorator extends TaskDecorator{
	private final double chance;
	
	
	public RandomDecorator(Blackboard blackboard, Task decorated, double chance) {
		super(blackboard, decorated);
		this.chance = chance;
	}

	@Override
	public void doAction() {
		decorated.doAction();
	}
	
	@Override
	public boolean isUpdatable() {
		if(RandomUtil.next()<chance)
			return decorated.isUpdatable();
		else
			return false;
		
	}
	

}
