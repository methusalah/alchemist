package model.world.terrain.heightmap;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.geometry.structure.grid.Grid;

public class HeightMap extends Grid<Height> {
	
	public HeightMap(int width, int height, Point2D coord) {
		super(width, height, coord);
		for(int i = 0; i < xSize*ySize; i++)
			set(i, new Height(i));
	}
	
	public HeightMap(@JsonProperty("width")int width,
			@JsonProperty("height")int height,
			@JsonProperty("flatData")double[] flatData,
			@JsonProperty("coord")Point2D coord) {
		super(width, height, coord);
		for(int i = 0; i < xSize*ySize; i++)
			set(i, new Height(i, flatData[i]));
	}
	
	public Point3D getPos(Height height){
		return new Point3D(getCoord(height.getIndex()), height.getElevation());
	}

	public double[] getFlatData(){
		double[] res = new double[xSize*ySize];
		for(int i = 0; i < xSize*ySize; i++)
			res[i] = get(i).getElevation();
		return res;
	}
	
	public int getWidth(){
		return xSize();
	}
	
	public int getHeight(){
		return ySize;
	}

	@JsonIgnore
	@Override
	public List<Height> getAll() {
		return super.getAll();
	}
}
