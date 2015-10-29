package model.world;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.ES.serial.Blueprint;
import model.ES.serial.EntityInstance;
import util.geometry.geom2d.Point2D;

public class Region {
	public static final int RESOLUTION = 64;

	private final String id;
	private List<EntityInstance> entities = new ArrayList<>();
	
	public Region(String id){
		this.id = id;
	}
	public Region(Point2D coord){
		this.id = RegionManager.getRegionId(coord);
	}
	
	public Region(@JsonProperty("id")String id,
			@JsonProperty("entities")List<EntityInstance> entities){
		this.id = id;
		this.entities = entities;
	}
	public List<EntityInstance> getEntities() {
		return entities;
	}
	public String getId() {
		return id;
	}
}
