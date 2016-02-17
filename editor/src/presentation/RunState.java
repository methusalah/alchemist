package presentation;

public class RunState {
	public enum State{Run, Stop, Pause}
	
	private final State state;
	
	public RunState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
}
