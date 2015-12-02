package util.event.scene;

import util.event.Event;
import view.controls.toolEditor.parameter.ToolPresenter;

public class ToolChangedEvent extends Event {
	
	private final ToolPresenter parameter;
	
	public ToolChangedEvent(ToolPresenter parameter) {
		this.parameter = parameter;
	}

	public ToolPresenter getParameter() {
		return parameter;
	}

}
