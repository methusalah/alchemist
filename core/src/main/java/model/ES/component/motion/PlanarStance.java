package model.ES.component.motion;


import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

import com.simsilica.es.EntityComponent;

public class PlanarStance implements EntityComponent {
	private final Point2D coord;
	private final double orientation;
	private final double elevation; 
	private final Point3D upVector;
	
	public PlanarStance(Point2D position, double orientation, double elevation, Point3D upVector) {
		this.coord = position;
		this.orientation = orientation;
		this.upVector = upVector;
		this.elevation = elevation;
	}
	
	
	public Point2D getCoord(){
		return coord;
	}
	
	public double getOrientation(){
		return orientation;
	}
	
	public Point3D getPlanarVector(){
		return Point2D.ORIGIN.getTranslation(orientation, 1).get3D(0);
	}

	public Point3D getUpVector() {
		return upVector;
	}

	public double getElevation() {
		return elevation;
	}
}
