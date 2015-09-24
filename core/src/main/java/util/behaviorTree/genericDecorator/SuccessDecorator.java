package util.behaviorTree.genericDecorator;

import util.behaviorTree.Blackboard;
import util.behaviorTree.Task;
import util.behaviorTree.TaskDecorator;

public class SuccessDecorator extends TaskDecorator {

	public SuccessDecorator(Blackboard blackboard, Task decorated) {
		super(blackboard, decorated);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		decorated.doAction();
		if(decorated.getControl().hasFailed())
			decorated.getControl().setSuccess();
	}
}
