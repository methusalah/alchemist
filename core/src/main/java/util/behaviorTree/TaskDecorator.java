package util.behaviorTree;

/**
 * Base class for the specific decorators. Decorates all the task methods except
 * for the DoAction, for commodity. (Tough any method can be decorated in the
 * base classes with no problem, they are decorated by default so the programmer
 * does not forget)
 * 
 * @author Ying
 *
 */
public abstract class TaskDecorator extends Task {
	protected Task decorated;

	public TaskDecorator(Blackboard blackboard, Task decorated) {
		this(blackboard, decorated, "unnamed decorator");
	}

	public TaskDecorator(Blackboard blackboard, Task decorated, String name) {
		super(blackboard, name);
		this.decorated = decorated;
		decorated.getControl().SetTask(this);
	}

	/**
	 * Decorate the CheckConditions
	 */
	@Override
	public boolean isUpdatable() {
		return decorated.isUpdatable();
	}

	/**
	 * Decorate the end
	 */
	@Override
	public void End() {
		decorated.End();
	}

	/**
	 * Decorate the request for the Control reference
	 */
	@Override
	public TaskController getControl() {
		return decorated.getControl();
	}

	/**
	 * Decorate the start
	 */
	@Override
	public void start() {
		decorated.start();
	}
}