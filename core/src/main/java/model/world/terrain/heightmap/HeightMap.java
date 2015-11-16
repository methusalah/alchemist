package model.world.terrain.heightmap;

import util.geometry.geom3d.Point3D;
import util.geometry.structure.grid.Grid;

public class HeightMap extends Grid<Height> {
	
	public HeightMap(int width, int height) {
		super(width, height);
		for(int i = 0; i < xSize*ySize; i++)
			set(i, new Height(i));
	}
	
	public Point3D getPos(Height height){
		return new Point3D(getCoord(height.getIndex()), height.getElevation());
	}
	

}
