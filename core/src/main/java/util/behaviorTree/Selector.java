package util.behaviorTree;

/**
 * This parent task selects one of it's children to update.
 * 
 * To select a child, it starts from the beginning of it's children vector and
 * goes one by one until it finds one that passes the CheckCondition test. It
 * then updates that child until it finished. If the child finishes with
 * failure, it continues down the list looking another candidate to update, and
 * if it doesn't find it, it finishes with failure. If the child finishes with
 * success, the Selector considers it's task done and bails with success.
 * 
 * @author Ying
 *
 */
public class Selector extends ParentTask {

	public Selector(Blackboard blackboard) {
		this(blackboard, "unnamed selector");
	}

	public Selector(Blackboard blackboard, String name) {
		super(blackboard, name);
	}

	/**
	 * In case of child finishing with failure we find a new one to update, or
	 * fail if none is to be found
	 */
	@Override
	public void notifyChildFailed() {
		control.setNextUpdatableTask();
		if (control.getCurrent() == null)
			control.setFailure();
	}

	/**
	 * In case of child finishing with sucess, our job here is done, finish with
	 * sucess as well
	 */
	@Override
	public void notifyChildSucceeded() {
		control.setSuccess();
	}
}