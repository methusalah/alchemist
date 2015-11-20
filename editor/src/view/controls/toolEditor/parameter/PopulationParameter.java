package view.controls.toolEditor.parameter;

import model.ES.serial.Blueprint;

public class PopulationParameter extends ToolParameter{
	private final Blueprint blueprint;
	
	public PopulationParameter(Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	public Blueprint getBlueprint() {
		return blueprint;
	}
	
	
	
}
