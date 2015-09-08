package model.ES.component.planarMotion;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class PlanarThrust implements EntityComponent {
	private final Point2D direction;
	
	public PlanarThrust(Point2D direction) {
		this.direction = direction;
	}

	public Point2D getDirection() {
		return direction;
	}
	
	
}
