package model.ES.component.motion;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class PlanarVelocityToApply implements EntityComponent {
	private final Point2D vector;
	
	public PlanarVelocityToApply(Point2D vector) {
		this.vector = vector;
	}

	public Point2D getVector() {
		return vector;
	}
	
	
}
