package model.ES.component.collision;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityId;

public class Collision {
	public final EntityId entityId;
	public final Point2D coord;
	public final Point2D normal;
	
	public Collision(EntityId entityId, Point2D coord, Point2D normal) {
		this.entityId = entityId;
		this.coord = coord;
		this.normal = normal;
	}
}
