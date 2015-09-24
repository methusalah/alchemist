package util.behaviorTree.genericDecorator;

import util.behaviorTree.Blackboard;
import util.behaviorTree.Task;
import util.behaviorTree.TaskDecorator;
import util.math.RandomUtil;

public class DelayDecorator extends TaskDecorator {
	private final double waitTime, waitRange;
	private long timer;
	private double toWait;
	
	public DelayDecorator(Blackboard blackboard, Task decorated, double waitTime, double waitRange) {
		super(blackboard, decorated);
		this.waitRange = waitRange;
		this.waitTime = waitTime;
	}

	@Override
	public void start() {
		super.start();
	}
	@Override
	public void doAction() {
		if(timer+toWait > System.currentTimeMillis()){
			resetTimer();
			decorated.doAction();
		}
	}
	
	private void resetTimer(){
		timer = System.currentTimeMillis();
		double range = waitRange != 0 ? RandomUtil.next()*waitRange : 0;
		toWait = waitTime + range;
	}
}
