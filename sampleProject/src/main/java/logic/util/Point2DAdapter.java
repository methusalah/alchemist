package logic.util;

import org.dyn4j.geometry.Vector2;

import util.geometry.geom2d.Point2D;

public class Point2DAdapter extends Point2D {
	public Point2DAdapter(Vector2 dyn4jVector2) {
		super(dyn4jVector2.x, dyn4jVector2.y);
	}

}
