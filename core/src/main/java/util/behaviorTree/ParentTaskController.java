package util.behaviorTree;

import java.util.ArrayList;
import java.util.List;

/**
 * This class extends the TaskController class to add support for child tasks
 * and their logic. Used together with ParentTask.
 * 
 * @author Ying
 *
 */
public class ParentTaskController extends TaskController {
	private List<Task> subtasks;
	private Task current;

	public ParentTaskController(Task task) {
		super(task);

		this.subtasks = new ArrayList<>();
		this.current = null;
	}

	public void add(Task task) {
		subtasks.add(task);
	}

	/**
	 * Resets the task as if it had just started.
	 */
	public void reset() {
		super.reset();
		current = subtasks.get(0);
	}

	
	List<Task> getSubtasks() {
		return subtasks;
	}

	Task getCurrent() {
		if(current == null)
			throw new NullPointerException();
		return current;
	}
	
	public void setNextUpdatableTask() {
		int index = subtasks.indexOf(current);
		
		while(index++ < subtasks.size()){
			if(subtasks.get(index).isUpdatable())
				current = subtasks.get(index); 
		}
		current = null;
	}
}
