package model.world;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityId;

import model.ES.serial.EntityInstance;
import model.world.terrain.Terrain;
import model.world.terrain.TerrainTexture;
import model.world.terrain.TerrainTexturing;
import util.geometry.geom2d.Point2D;

public class Region {
	public static final int RESOLUTION = 64;

	private final String id;
	private final Point2D coord;
	private final List<EntityInstance> entities;
	private final Terrain terrain;
	
	private EntityId entityId;
	private List<EntityId> terrainColliders = new ArrayList<EntityId>();
	
	private boolean modified = false;

	public Region(String id, Point2D coord){
		this.id = id;
		this.coord = coord;
		TerrainTexturing t = new TerrainTexturing(
				new TerrainTexture("textures/grass02.jpg", null, 32),
				new TerrainTexture("textures/paving2full.png", null, 32),
				new TerrainTexture("textures/trans.png", null, 32));
		terrain = new Terrain(RESOLUTION+1, RESOLUTION+1, t, coord);
		entities = new ArrayList<>();
	}
	public Region(Point2D coord){
		this(RegionLoader.getRegionId(coord), RegionLoader.getRegionCoord(coord));
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
	
	@JsonIgnore
	public boolean isModified() {
		return modified;
	}
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	@JsonIgnore
	public EntityId getEntityId() {
		return entityId;
	}
	
	public void setEntityId(EntityId entityId) {
		this.entityId = entityId;
	}

	@JsonIgnore
	public List<EntityId> getTerrainColliders() {
		return terrainColliders;
	}
	
	public void setTerrainColliders(List<EntityId> terrainColliders) {
		this.terrainColliders = terrainColliders;
	}
	
}
