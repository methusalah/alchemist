package util.behaviorTree;

/**
 * Class added by composition to any task, to keep track of the Task state and
 * logic flow.
 * 
 * This state-control class is separated from the Task class so the Decorators
 * have a chance at compile-time security.
 * 
 * @author Ying
 *
 */
public class TaskController {
	private boolean done;
	private boolean sucess;
	private boolean started;

	private Task task;

	public TaskController(Task task) {
		SetTask(task);
		done = false;
		sucess = true;
		started = false;
	}

	/**
	 * Sets the task reference
	 * 
	 * @param task
	 *            Task to monitor
	 */
	public void SetTask(Task task) {
		this.task = task;
	}

	/**
	 * Starts the monitored class
	 */
	public void SafeStart() {
		this.started = true;
		task.start();
	}

	/**
	 * Ends the monitored task
	 */
	public void SafeEnd() {
		this.done = false;
		this.started = false;
		task.End();
	}

	/**
	 * Ends the monitored class, with success
	 */
	public void setSuccess() {
		this.sucess = true;
		this.done = true;
		task.log("Finished with success");
	}

	/**
	 * Ends the monitored class, with failure
	 */
	public void setFailure() {
		this.sucess = false;
		this.done = true;
		task.log("Finished with failure");
	}

	/**
	 * Indicates whether the task finished successfully
	 * 
	 * @return True if it did, false if it didn't
	 */
	public boolean hasSucceeded() {
		return this.sucess;
	}

	/**
	 * Indicates whether the task finished with failure
	 * 
	 * @return True if it did, false if it didn't
	 */
	public boolean hasFailed() {
		return !this.sucess;
	}

	/**
	 * Indicates whether the task finished
	 * 
	 * @return True if it did, false if it didn't
	 */
	public boolean isFinished() {
		return this.done;
	}

	/**
	 * Indicates whether the class has started or not
	 * 
	 * @return True if it has, false if it hasn't
	 */
	public boolean isStarted() {
		return this.started;
	}

	/**
	 * Marks the class as just started.
	 */
	public void reset() {
		this.done = false;
	}
}