package model.state;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import model.tempImport.TranslateUtil;

public abstract class PointUtil {

	public static Geometry getPointedGeometry(Node n, Ray r) {
		CollisionResult collision = getCollision(n, r);
		if (collision == null) {
			return null;
		}
		return collision.getGeometry();
	}

	public static Point2D getPointedCoord(Node n, Ray r) {
		CollisionResult collision = getCollision(n, r);
		if (collision == null) {
			return null;
		}
		// return Translator.toPoint2D(collision.getContactPoint());
		Vector3f p = collision.getContactPoint();
		return new Point2D(p.x, p.y);
	}

	public static Point3D getPointedCoord3D(Node n, Ray r) {
		CollisionResult collision = getCollision(n, r);
		if (collision == null) {
			return null;
		}
		return TranslateUtil.toPoint3D(collision.getContactPoint());
	}

	private static CollisionResult getCollision(Node n, Ray r) {
		CollisionResults results = new CollisionResults();
		n.collideWith(r, results);
		if (results.size() == 0) {
			return null;
		}
		return results.getClosestCollision();
	}
}
