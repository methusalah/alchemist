package model.world.terrain;

import util.geometry.geom2d.Point2D;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.ES.component.world.TerrainTexturing;
import model.world.terrain.atlas.Atlas;
import model.world.terrain.heightmap.HeightMap;
import model.world.terrain.heightmap.Parcelling;

public final class Terrain {
	private final Parcelling parcelling;
	private final Atlas atlas, cover;
	private final HeightMap heighMap;
	private final TerrainTexturing texturing;
	
	private final int width, height;
	
	public Terrain(@JsonProperty("width")int width,
			@JsonProperty("height")int height,
			@JsonProperty("atlas")Atlas atlas,
			@JsonProperty("cover")Atlas cover,
			@JsonProperty("heightMap")HeightMap heightMap,
			@JsonProperty("texturing")TerrainTexturing texturing){
		this.width = width;
		this.height = height;

		this.atlas = atlas;
		this.cover = cover;
		this.texturing = texturing; 
		this.heighMap = heightMap;
		parcelling = new Parcelling(heighMap);
	}
	
	public Terrain(int width, int height, TerrainTexturing texturing, Point2D coord) {
		this.width = width;
		this.height = height;
		this.texturing = texturing; 

		atlas = new Atlas(width, height);
		cover = new Atlas(width, height);
		heighMap = new HeightMap(width, height, coord);
		
		parcelling = new Parcelling(heighMap);
	}
	
	
	public Atlas getAtlas() {
		return atlas;
	}

	public Atlas getCover() {
		return cover;
	}

	@JsonIgnore
	public Parcelling getParcelling() {
		return parcelling;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public TerrainTexturing getTexturing() {
		return texturing;
	}
	
	public HeightMap getHeightMap(){
		return heighMap;
	}
}
