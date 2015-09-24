package util.behaviorTree;

/**
 * This ParentTask executes each of it's children in turn until he has finished
 * all of them.
 * 
 * It always starts by the first child, updating each one. If any child finishes
 * with failure, the Sequence fails, and we finish with failure. When a child
 * finishes with success, we select the next child as the update victim. If we
 * have finished updating the last child, the Sequence returns with success.
 * 
 * @author Ying
 *
 */
public class Sequence extends ParentTask {
	public Sequence(Blackboard blackboard) {
		this(blackboard, "unnamed sequence");
	}

	public Sequence(Blackboard blackboard, String name) {
		super(blackboard, name);
	}

	/**
	 * A child finished with failure. We failed to update the whole sequence.
	 * Bail with failure.
	 */
	@Override
	public void notifyChildFailed() {
		control.setFailure();
	}

	/**
	 * A child has finished with success Select the next one to update. If it's
	 * the last, we have finished with success.
	 */
	@Override
	public void notifyChildSucceeded() {
		control.setNextUpdatableTask();
		if(control.getCurrent() == null)
			// TODO beware that an un-updatable task leads to success. It may not be desirable
			control.setSuccess();
	}
}
