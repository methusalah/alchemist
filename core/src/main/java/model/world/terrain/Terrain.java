package model.world.terrain;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.ES.component.world.TerrainTexturing;
import model.world.terrain.atlas.Atlas;
import model.world.terrain.heightmap.HeightMap;
import model.world.terrain.heightmap.Parcelling;

public final class Terrain {
	private final Parcelling parcelling;
	private final Atlas atlas, cover;
	private final HeightMap heighmap;
	private final TerrainTexturing texturing;
	
	private final int width, height;
	
	public Terrain(@JsonProperty("width")int width,
			@JsonProperty("height")int height,
			@JsonProperty("atlas")Atlas atlas,
			@JsonProperty("cover")Atlas cover,
			@JsonProperty("texturing")TerrainTexturing texturing){
		this.width = width;
		this.height = height;

		this.atlas = atlas;
		this.cover = cover;
		this.texturing = texturing; 
		heighmap = new HeightMap(width, height);
		parcelling = new Parcelling(heighmap);
	}
	
	public Terrain(int width, int height, TerrainTexturing texturing) {
		this.width = width;
		this.height = height;
		atlas = new Atlas(width, height);
		cover = new Atlas(width, height);
		heighmap = new HeightMap(width, height);
		
		parcelling = new Parcelling(heighmap);
		this.texturing = texturing; 
	}
	
	
	public Atlas getAtlas() {
		return atlas;
	}

	public Atlas getCover() {
		return cover;
	}

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
}
