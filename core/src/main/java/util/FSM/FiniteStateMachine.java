package util.FSM;

import java.util.ArrayList;

import util.LogUtil;

/**
 * A simple Final State Machine for the Tactical AI states
 */
public class FiniteStateMachine {
	protected ArrayList<StateActor> states = new ArrayList<>();
	public void update() {
		if (states.isEmpty()) {
			return;
		}
		if(states.size()> 1){
			String log = " states : ";
			for(StateActor s : states)
				log = log + s.getClass().getSimpleName()+" / "; 
				
			LogUtil.info(log);
		}
		StateActor current = states.get(0);
		current.execute();
		if(current.isToPop())
			pop();
		for(StateActor toPush : current.grabToPush())
			pushState(toPush);

	}

	public void pop() {
		states.remove(0);
	}

	public void pushState(StateActor state) {
		states.add(0, state);
	}

	public void popAll() {
		states.clear();
	}
}
