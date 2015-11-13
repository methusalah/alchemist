package model.world.terrain.heightmap;

import java.util.ArrayList;
import java.util.List;

import util.geometry.structure.grid.Node;

public class Height extends Node {
	
	private double elevation;
	
	public Height(int index) {
		super(index);
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
}
