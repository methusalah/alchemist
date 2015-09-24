package util.behaviorTree;

/**
 * Inner node of the behavior tree, a flow director node, that selects a child
 * to be executed next.
 * 
 * Sets a specific kind of TaskController for these kinds of tasks.
 * 
 * @author Ying
 *
 */
abstract class ParentTask extends Task {
	ParentTaskController control;

	public ParentTask(Blackboard blackboard) {
		this(blackboard, "unnamed parent task");
	}

	public ParentTask(Blackboard blackboard, String name) {
		super(blackboard, name);
		this.control = new ParentTaskController(this);
	}

	@Override
	public TaskController getControl() {
		return control;
	}

	/**
	 * Checks for the appropiate pre-state of the data
	 */
	@Override
	public boolean isUpdatable() {
		log("Checking conditions");
		return control.getSubtasks().size() > 0;
	}

	/**
	 * Called when a child finishes with success.
	 */
	public abstract void notifyChildSucceeded();

	/**
	 * Called when a child finishes with failure.
	 */
	public abstract void notifyChildFailed();

	/**
	 * Checks whether the child has started, finished or needs updating, and
	 * takes the needed measures in each case
	 */
	@Override
	public void doAction() {
		log("Doing action");
		if (control.isFinished())
			return;

		TaskController currentTaskCtrl = control.getCurrent().getControl();
		// If we do have a curTask...
		if(!currentTaskCtrl.isStarted())
			// ... and it's not started yet, start it.
			currentTaskCtrl.SafeStart();
		else if(currentTaskCtrl.isFinished()) {
			// ... and it's finished, end it properly.
			currentTaskCtrl.SafeEnd();

			if (currentTaskCtrl.hasSucceeded()) {
				notifyChildSucceeded();
			}
			if (currentTaskCtrl.hasFailed()) {
				notifyChildFailed();
			}
		} else {
			// ... and it's ready, update it.
			control.getCurrent().doAction();
		}
	}

	/**
	 * Ends the task
	 */
	@Override
	public void End() {
		log("Ending");
	}

	/**
	 * Starts the task, and points the current task to the first one of the
	 * available child tasks.
	 */
	@Override
	public void start() {
		log("Starting");
		control.reset();
	}
}