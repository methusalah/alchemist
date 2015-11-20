package view.controls.toolEditor.parameter;

import model.world.HeightMapTool.OPERATION;
import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;

public class HeightMapParameter extends ToolParameter {
	private final OPERATION operation;
	private final double size;
	private final double strength;
	private final SHAPE shape;
	private final MODE mode;
	
	public HeightMapParameter(OPERATION operation,
			double size,
			double strength,
			SHAPE shape,
			MODE mode) {
		this.operation = operation;
		this.size = size;
		this.strength = strength;
		this.shape = shape;
		this.mode = mode;
	}

	public OPERATION getOperation() {
		return operation;
	}

	public double getSize() {
		return size;
	}

	public double getStrength() {
		return strength;
	}

	public SHAPE getShape() {
		return shape;
	}

	public MODE getMode() {
		return mode;
	}
	
	
	
}
