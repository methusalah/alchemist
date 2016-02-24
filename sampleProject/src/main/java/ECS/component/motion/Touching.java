package ECS.component.motion;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.geometry.geom2d.Point2D;

public class Touching implements EntityComponent {
	private final EntityId touched;
	private final Point2D coord;
	private final Point2D normal;
	
	public Touching() {
		touched = null;
		coord = Point2D.ORIGIN;
		normal = Point2D.ORIGIN;
	}

	public Touching(EntityId touched, Point2D coord, Point2D normal) {
		this.touched = touched;
		this.coord = coord;
		this.normal = normal;
	}

	public EntityId getTouched() {
		return touched;
	}

	public Point2D getCoord() {
		return coord;
	}

	public Point2D getNormal() {
		return normal;
	}
}
