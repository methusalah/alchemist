package model.world.terrain.heightmap;

import util.geometry.structure.grid.Node;

public class Height extends Node {
	
	private double elevation;
	
	public Height(int index) {
		super(index);
	}
	
	public Height(int index, double elevation) {
		super(index);
		this.elevation = elevation;
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
