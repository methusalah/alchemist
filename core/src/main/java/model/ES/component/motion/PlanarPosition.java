package model.ES.component.motion;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class PlanarPosition implements EntityComponent {
	private final Point2D position;
	private final double orientation;
	
	public PlanarPosition(Point2D position, double orientation) {
		this.position = position;
		this.orientation = orientation;
	}
	
	public PlanarPosition(double x, double y, double orientation){
		this.position = new Point2D(x, y);
		this.orientation = orientation;
	}
	
	public Point2D getPosition(){
		return position;
	}
	
	public double getOrientation(){
		return orientation;
	}
}
