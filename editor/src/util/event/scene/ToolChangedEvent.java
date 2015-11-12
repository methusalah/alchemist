package util.event.scene;

import model.ES.serial.Blueprint;
import util.event.Event;

public class ToolChangedEvent extends Event {
	
	private final Blueprint bp;
	
	public ToolChangedEvent(Blueprint bp) {
		this.bp = bp;
	}

	public Blueprint getBp() {
		return bp;
	}

	
}
