package model.world;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.ES.serial.Blueprint;
import model.ES.serial.EntityInstance;
import model.world.terrain.Terrain;
import model.world.terrain.heightmap.Parcel;
import model.world.terrain.heightmap.Parcelling;
import util.geometry.geom2d.Point2D;

public class Region {
	public static final int RESOLUTION = 64;

	private final String id;
	private List<EntityInstance> entities = new ArrayList<>();
	private Terrain terrain;

	public Region(String id){
		this.id = id;
		//terrain = new Terrain(RESOLUTION, RESOLUTION, null);
	}
	public Region(Point2D coord){
		this(RegionManager.getRegionId(coord));
	}
	
	public Region(@JsonProperty("id")String id,
			@JsonProperty("entities")List<EntityInstance> entities,
			@JsonProperty("terrain")Terrain terrain){
		this.id = id;
		this.entities = entities;
		this.terrain = terrain;
	}
	
	public List<EntityInstance> getEntities() {
		return entities;
	}
	
	public String getId() {
		return id;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
}
