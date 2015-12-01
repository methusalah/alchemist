package view.controls.toolEditor.parameter;

import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;

public class PencilPresenter extends ToolPresenter {
	private double size = 4;
	private double strength = 0.5;
	private SHAPE shape = SHAPE.Circle;
	private MODE mode = MODE.Airbrush;
	
	
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
