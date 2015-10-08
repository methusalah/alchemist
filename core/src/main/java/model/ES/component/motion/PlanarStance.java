package model.ES.component.motion;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import model.ES.serial.EditorInfo;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

public class PlanarStance implements EntityComponent{
	
	@EditorInfo(UIname="Coordinate", info="Actual coordinate of the entity.")
	public final Point2D coord;
	
	@EditorInfo(UIname="Orientation", info="Orientation angle in radians.")
	public final double orientation;
	
	@EditorInfo(UIname="Elevation", info="Z value.")
	public final double elevation; 

	@EditorInfo(UIname="Up vector", info="Vector indicating the top vector to tilt the entity.")
	public final Point3D upVector;
	
	public PlanarStance(@JsonProperty("coord")Point2D coord,
			@JsonProperty("orientation")double orientation,
			@JsonProperty("elevation")double elevation,
			@JsonProperty("upVector")Point3D upVector) {
		this.coord = coord;
		this.orientation = orientation;
		this.upVector = upVector;
		this.elevation = elevation;
	}
	
	public Point2D getCoord() {
		return coord;
	}

	public double getOrientation() {
		return orientation;
	}
	
	public double getElevation() {
		return elevation;
	}

	public Point3D getUpVector() {
		return upVector;
	}
}
