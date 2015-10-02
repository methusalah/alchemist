package model.ES.component.motion;


import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

import com.simsilica.es.EntityComponent;

public class PlanarStance implements EntityComponent {
	public final Point2D coord;
	public final double orientation;
	public final double elevation; 
	public final Point3D upVector;
	
	public PlanarStance(Point2D coord, double orientation, double elevation, Point3D upVector) {
		this.coord = coord;
		this.orientation = orientation;
		this.upVector = upVector;
		this.elevation = elevation;
	}
}
