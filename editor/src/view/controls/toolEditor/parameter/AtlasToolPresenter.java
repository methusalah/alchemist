package view.controls.toolEditor.parameter;

import model.world.atlas.AtlasTool.OPERATION;

public class AtlasToolPresenter extends PencilPresenter {
	private OPERATION operation = OPERATION.Add_Delete;

	public OPERATION getOperation() {
		return operation;
	}

	public void setOperation(OPERATION operation) {
		this.operation = operation;
	}
}
