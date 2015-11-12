package model.world.terrain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.geometry.structure.grid3D.Grid3D;
import util.geometry.structure.grid3D.Node3D;

public class TerrainNode extends Node3D {

	public TerrainNode(Grid3D<? extends Node3D> grid, int index) {
		super(grid, index);
	}
	
	@JsonIgnore
	public TerrainNode n() {
		return getTerrain().getNorthNode(this);
	}
	@JsonIgnore
	public TerrainNode s() {
		return getTerrain().getSouthNode(this);
	}
	@JsonIgnore
	public TerrainNode e() {
		return getTerrain().getEastNode(this);
	}
	@JsonIgnore
	public TerrainNode w() {
		return getTerrain().getWestNode(this);
	}
	
	@JsonIgnore
	public Terrain getTerrain(){
		return (Terrain)grid;
	}
	
	@JsonIgnore
	public Point2D getCoord(){
		return getTerrain().getCoord(index);
	}
	
	@JsonIgnore
	public Point3D getPos(){
		return getTerrain().getPos(this);
	}

}
