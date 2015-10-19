package model.ES.component.motion;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import model.ES.serial.EditorInfo;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;

public class PlanarStance implements EntityComponent{
	public final Point2D coord;
	public final Angle orientation;
	public final double elevation; 
	public final Point3D upVector;
	
	public PlanarStance() {
		coord = Point2D.ORIGIN;
		orientation = new Angle(0);
		elevation = 0;
		upVector = Point3D.UNIT_Z;
	}
	
	public PlanarStance(@JsonProperty("coord")Point2D coord,
			@JsonProperty("orientation")Angle orientation,
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

	public Angle getOrientation() {
		return orientation;
	}
	
	public double getElevation() {
		return elevation;
	}

	public Point3D getUpVector() {
		return upVector;
	}
}
