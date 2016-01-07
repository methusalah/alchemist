package presenter;

public class RunState {
	enum State{Run, Stop, Pause}
	
	private final State state;
	
	public RunState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
}
