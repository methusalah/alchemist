package util.behaviorTree;

/**
 * Leaf task (or node) in the behavior tree.
 * 
 * Specifies a TaskControler, by composition, to take care of all the control
 * logic, without burdening the Task class with complications.
 * 
 * @author Ying
 *
 */
public abstract class LeafTask extends Task {
	/**
	 * Task controler to keep track of the Task state.
	 */
	protected TaskController control;

	/**
	 * Creates a new instance of the LeafTask class
	 * 
	 * @param blackboard
	 *            Reference to the AI Blackboard data
	 */
	public LeafTask(Blackboard blackboard) {
		this(blackboard, "unnamed leaf task");
	}

	/**
	 * Creates a new instance of the LeafTask class
	 * 
	 * @param blackboard
	 *            Reference to the AI Blackboard data
	 * @param name
	 *            Name of the class for debugging
	 */
	public LeafTask(Blackboard blackboard, String name) {
		super(blackboard, name);
		control = new TaskController(this);
	}

	/**
	 * Gets the controller reference.
	 */
	@Override
	public TaskController getControl() {
		return control;
	}
}
