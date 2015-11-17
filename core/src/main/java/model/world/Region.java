package model.world;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.ES.component.world.TerrainTexturing;
import model.ES.serial.Blueprint;
import model.ES.serial.EntityInstance;
import model.world.terrain.Terrain;
import model.world.terrain.heightmap.Parcel;
import model.world.terrain.heightmap.Parcelling;
import util.geometry.geom2d.Point2D;

public class Region {
	public static final int RESOLUTION = 15;

	private final String id;
	private final Point2D coord;
	private List<EntityInstance> entities = new ArrayList<>();
	private Terrain terrain;

	public Region(String id, Point2D coord){
		this.id = id;
		this.coord = coord;
		TerrainTexturing t = new TerrainTexturing(new ArrayList<String>(){{add("textures/grass02.jpg");add("textures/dirt.jpg");}},
				new ArrayList<String>(){{add(null);add(null);}},
				new ArrayList<Double>(){{add(32d);add(32d);}},
				new ArrayList<String>(){{add("textures/transp.png");}},
				new ArrayList<String>(){{add(null);}},
				new ArrayList<Double>(){{add(1d);}});
		terrain = new Terrain(RESOLUTION, RESOLUTION, t);
	}
	public Region(Point2D coord){
		this(RegionManager.getRegionId(coord), RegionManager.getRegionCoord(coord));
	}
	
	public Region(@JsonProperty("id")String id,
			@JsonProperty("coord")Point2D coord,
			@JsonProperty("entities")List<EntityInstance> entities,
			@JsonProperty("terrain")Terrain terrain){
		this.id = id;
		this.coord = coord;
		this.entities = entities;
		this.terrain = terrain;
	}
	
	public List<EntityInstance> getEntities() {
		return entities;
	}
	
	public String getId() {
		return id;
	}
	
	public Point2D getCoord() {
		return coord;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
}
