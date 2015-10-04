package model.ES.component.visuals;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.geometry.geom3d.Point3D;

public class Skeleton implements EntityComponent{
	public final HashMap<String, Point3D> bonePositions;
	public final HashMap<String, Point3D> boneDirections;
	public final boolean initialized;
	
	public Skeleton(@JsonProperty("bonePositions")HashMap<String, Point3D> bonePositions,
			@JsonProperty("boneDirections")HashMap<String, Point3D> boneDirections,
			@JsonProperty("initialized")boolean initialized) {
		this.bonePositions = bonePositions;
		this.boneDirections = boneDirections;
		this.initialized = initialized;
	}
}
