package model.ES.component.motion;


import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import model.ES.serial.EditorInfo;

public class PlanarStance implements EntityComponent {
	
	@EditorInfo(UIname="Coordinate", info="Actual coordinate of the entity.")
	public final Point2D coord;
	public final double orientation;
	public final double elevation; 
	public final Point3D upVector;
	
	public PlanarStance(@JsonProperty("coord")Point2D coord, @JsonProperty("orientation")double orientation, @JsonProperty("elevation")double elevation, @JsonProperty("upVector")Point3D upVector) {
		this.coord = coord;
		this.orientation = orientation;
		this.upVector = upVector;
		this.elevation = elevation;
	}
}
