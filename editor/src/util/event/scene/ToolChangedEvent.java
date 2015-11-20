package util.event.scene;

import util.event.Event;
import view.controls.toolEditor.parameter.ToolParameter;

public class ToolChangedEvent extends Event {
	
	private final ToolParameter parameter;
	
	public ToolChangedEvent(ToolParameter parameter) {
		this.parameter = parameter;
	}

	public ToolParameter getParameter() {
		return parameter;
	}

}
