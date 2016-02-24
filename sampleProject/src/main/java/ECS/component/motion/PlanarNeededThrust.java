package ECS.component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.geometry.geom2d.Point2D;


public class PlanarNeededThrust implements EntityComponent {
	private final Point2D direction;
	
	public PlanarNeededThrust() {
		direction = Point2D.ORIGIN;
	}
	
	public PlanarNeededThrust(@JsonProperty("direction")Point2D direction) {
		this.direction = direction;
	}

	public Point2D getDirection() {
		return direction;
	}
}
