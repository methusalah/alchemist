package model.ES.component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.geometry.geom3d.Point3D;

public class SpaceStance implements EntityComponent{
	public final Point3D position;
	public final Point3D direction;
	
	public SpaceStance(@JsonProperty("position")Point3D position, @JsonProperty("direction")Point3D direction) {
		this.position = position;
		this.direction = direction;
	}
}
