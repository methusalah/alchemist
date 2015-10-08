package model.ES.component.motion;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import model.ES.serial.EditorInfo;

public class PlanarStance implements EntityComponent {
	
	@EditorInfo(UIname="Coordinate", info="Actual coordinate of the entity.")
	public final Point2D coord;
	
	@EditorInfo(UIname="Orientation", info="Orientation angle in radians.")
	public final double orientation;
	
	@EditorInfo(UIname="Elevation", info="Z value.")
	public final double elevation; 

	@EditorInfo(UIname="Up vector", info="Vector indicating the top to tilt the entity.")
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
	
	public static BeanInfo getBeanInfo(){
		try {
			return Introspector.getBeanInfo(PlanarStance.class);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
