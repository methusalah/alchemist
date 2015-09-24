package util.behaviorTree;

/**
 * Base abstract class for all the tasks in the behavior tree.
 * 
 * DO NOT ADD UTILITY FUNCTIONS. This class must remain as much a interface as
 * possible, otherwise the decorators have no security mechanism to ensure they
 * are properly acting as wrappers of their specific tasks.
 * 
 * @author Ying
 *
 */
public abstract class Task {
	protected Blackboard bb;
	protected String name;

	public Task(Blackboard blackboard) {
		this(blackboard, "unnamed task");
	}

	public Task(Blackboard blackboard, String name) {
		this.name = name;
		this.bb = blackboard;
	}

	/**
	 * Logs the Task reference plus a message.
	 * 
	 * @param text
	 *            Message to display
	 */
	public void log(String text) {
		// Log.i("Task", "Task: " + name + "; Player: " + bb.player.GetID() + ";
		// " + text);
	}

	/**
	 * Override to do a pre-conditions check to see if the task can be updated.
	 * 
	 * @return True if it can, false if it can't
	 */
	public abstract boolean isUpdatable();

	/**
	 * Override to add startup logic to the task
	 */
	public abstract void start();

	/**
	 * Override to add ending logic to the task
	 */
	public abstract void End();

	/**
	 * Override to specify the logic the task must update each cycle
	 */
	public abstract void doAction();

	/**
	 * Override to specify the controller the task has
	 * 
	 * @return The specific task controller.
	 */
	public abstract TaskController getControl();
}
