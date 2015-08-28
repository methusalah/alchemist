package model.ES.component.motion;

import com.simsilica.es.EntityComponent;

import util.geometry.geom2d.Point2D;

public class PlanarInSightDestination implements EntityComponent{
	private final Point2D coord;
	
	public PlanarInSightDestination(Point2D coord) {
		this.coord = coord;
	}

	public Point2D getCoord() {
		return coord;
	}
}
