package view.controls.toolEditor.parameter;

import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;
import model.world.atlas.AtlasTool.OPERATION;

public class AtlasParameter extends ToolParameter {
	private OPERATION operation = OPERATION.Add_Delete;
	private double size = 4;
	private double strength = 0.5;
	private SHAPE shape = SHAPE.Circle;
	private MODE mode = MODE.Airbrush;
	
	public AtlasParameter(){
		
	}
	
	public AtlasParameter(OPERATION operation,
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

	public void setOperation(OPERATION operation) {
		this.operation = operation;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	public SHAPE getShape() {
		return shape;
	}

	public void setShape(SHAPE shape) {
		this.shape = shape;
	}

	public MODE getMode() {
		return mode;
	}

	public void setMode(MODE mode) {
		this.mode = mode;
	}

}
