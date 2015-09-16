package model.ES.component.collision;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class Collision implements EntityComponent {
	private final EntityId a, b;
	private final Point2D coord;
	private final Point2D normal;
	
	public Collision(EntityId a, EntityId b, Point2D coord, Point2D normal) {
		this.a = a;
		this.b = b;
		this.coord = coord;
		this.normal = normal;
	}

	public EntityId getA() {
		return a;
	}

	public EntityId getB() {
		return b;
	}

	public Point2D getCoord() {
		return coord;
	}

	public Point2D getNormal() {
		return normal;
	}
}
