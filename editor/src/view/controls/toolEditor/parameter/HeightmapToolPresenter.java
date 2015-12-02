package view.controls.toolEditor.parameter;

import model.world.HeightMapTool.OPERATION;
import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;

public class HeightmapToolPresenter extends PencilPresenter {
	private OPERATION operation = OPERATION.Raise_Low;

	public OPERATION getOperation() {
		return operation;
	}

	public void setOperation(OPERATION operation) {
		this.operation = operation;
	}
}
