package util.behaviorTree.genericDecorator;

import util.behaviorTree.Blackboard;
import util.behaviorTree.Task;
import util.behaviorTree.TaskController;
import util.behaviorTree.TaskDecorator;

public class InvertDecorator extends TaskDecorator {

	public InvertDecorator(Blackboard blackboard, Task decorated) {
		super(blackboard, decorated);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		decorated.doAction();
		
		TaskController ctrl = decorated.getControl();
		
		if(ctrl.hasSucceeded())
			ctrl.setFailure();
		else if(ctrl.hasFailed())
			ctrl.setSuccess();
	}

}
