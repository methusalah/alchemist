package logic.util;

import org.dyn4j.geometry.Vector2;

import util.geometry.geom2d.Point2D;

public class Vector2Adapter extends Vector2 {

	public Vector2Adapter(Point2D point) {
		super(point.x, point.y);
	}
}
