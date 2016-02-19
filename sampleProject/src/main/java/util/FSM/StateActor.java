package util.FSM;

import java.util.ArrayList;
import java.util.List;

public abstract class StateActor {
	
	private List<StateActor> toPush = new ArrayList<>();
	private boolean toPop = false;
	
	protected abstract void execute();
	
	protected void endExecution(){
		toPop = true;
	}
	
	protected void pushState(StateActor state){
		toPush.add(state);
	}

	List<StateActor> grabToPush() {
		List<StateActor> res = toPush;
		toPush = new ArrayList<>();
		return res;
	}

	boolean isToPop() {
		return toPop;
	}

}
