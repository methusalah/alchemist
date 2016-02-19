package util.geometry.structure.grid3D;

import util.geometry.structure.grid.GridNode;

public class Node3D extends GridNode {

	protected double elevation;
	
	public Node3D(int index) {
		super(index);
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	
	public void elevate(double val){
		elevation += val;
	}
}
