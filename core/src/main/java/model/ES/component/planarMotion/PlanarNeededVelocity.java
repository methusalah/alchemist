package model.ES.component.planarMotion;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class PlanarNeededVelocity implements EntityComponent {
	private final Point2D direction;
	
	public PlanarNeededVelocity(Point2D direction) {
		this.direction = direction;
	}

	public Point2D getDirection() {
		return direction;
	}
	
	
}
