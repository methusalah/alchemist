package controller;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import view.PointUtil;
import view.math.TranslateUtil;
import app.AppFacade;

import com.jme3.input.InputManager;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class SpatialSelector {

	public boolean centered = false;

	public SpatialSelector() {
	}

	public Geometry getGeometry(Node n) {
		Ray r;
		if (centered) {
			r = getCameraRay();
		} else {
			r = getMouseRay();
		}
		return PointUtil.getPointedGeometry(n, r);
	}

	public Point2D getCoord(Node n) {
		Ray r;
		if (centered) {
			r = getCameraRay();
		} else {
			r = getMouseRay();
		}
		return PointUtil.getPointedCoord(n, r);
	}

	public Point2D getCenterViewCoord(Node n) {
		return PointUtil.getPointedCoord(n, getCameraRay());
	}

	public static Point2D getCoord(Node n, Point2D screenCoord) {
		Vector3f origin = AppFacade.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 0f);
		Vector3f direction = AppFacade.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 1f);
		direction.subtractLocal(origin).normalizeLocal();
		Ray r = new Ray(origin, direction);
		return PointUtil.getPointedCoord(n, r);
	}

	public static Geometry getPointedGeometry(Node n, Point2D screenCoord) {
		Vector3f origin = AppFacade.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 0f);
		Vector3f direction = AppFacade.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 1f);
		direction.subtractLocal(origin).normalizeLocal();
		Ray r = new Ray(origin, direction);
		return PointUtil.getPointedGeometry(n, r);
	}

	public Point2D getScreenCoord(Point3D pos) {
		Vector3f vPos = TranslateUtil.toVector3f(pos);
		Vector3f screenCoord = AppFacade.getCamera().getScreenCoordinates(vPos);
		return TranslateUtil.toPoint3D(screenCoord).get2D();
	}

	private Ray getMouseRay() {
		Vector3f origin = AppFacade.getCamera().getWorldCoordinates(AppFacade.getInputManager().getCursorPosition(), 0f);
		Vector3f direction = AppFacade.getCamera().getWorldCoordinates(AppFacade.getInputManager().getCursorPosition(), 1f);
		direction.subtractLocal(origin).normalizeLocal();
		return new Ray(origin, direction);
	}

	private Ray getCameraRay() {
		return new Ray(AppFacade.getCamera().getLocation(), AppFacade.getCamera().getDirection());
	}
}
