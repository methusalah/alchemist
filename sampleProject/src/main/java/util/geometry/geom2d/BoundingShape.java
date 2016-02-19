package util.geometry.geom2d;

import java.util.List;

import util.geometry.geom2d.algorithm.Collider;
import util.geometry.geom2d.algorithm.Container;

/**
 *
 * 
 */
abstract public class BoundingShape {
	public boolean collide(List<BoundingShape> shapes) {
		for(BoundingShape s : shapes) {
			if(collide(s)) {
				return true;
			}
		}
		return false;
	}

	abstract public Point2D getCenter();

	public boolean collide(BoundingShape shape) {
		return Collider.areColliding(this,  shape);
	}

	public boolean contains(BoundingShape shape) {
		return Container.containsIn(this, shape);
	}
}
