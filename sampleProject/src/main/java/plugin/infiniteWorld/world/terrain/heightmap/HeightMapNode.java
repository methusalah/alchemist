package plugin.infiniteWorld.world.terrain.heightmap;

import util.geometry.structure.grid.GridNode;

public class HeightMapNode extends GridNode {
	
	private double elevation;
	
	public HeightMapNode(int index) {
		super(index);
	}
	
	public HeightMapNode(int index, double elevation) {
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
