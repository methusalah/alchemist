package model.ES.component.motion;


import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

import com.simsilica.es.EntityComponent;

public class PlanarPosition implements EntityComponent {
	private final Point2D position;
	private final double orientation;
	private final Point3D upVector;
	
	public PlanarPosition(Point2D position, double orientation) {
		this(position, orientation, Point3D.UNIT_Z);
	}

	public PlanarPosition(Point2D position, double orientation, Point3D upVector) {
		this.position = position;
		this.orientation = orientation;
		this.upVector = upVector;
	}
	
	
	public Point2D getPosition(){
		return position;
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
}
