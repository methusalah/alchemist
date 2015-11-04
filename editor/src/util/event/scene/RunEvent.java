package util.event.scene;

import util.event.Event;

public class RunEvent extends Event {
	public boolean value;
	
	public RunEvent(boolean value) {
		this.value = value;
	}
}
