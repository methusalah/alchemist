package com.brainless.alchemist.model.state;

import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

public class SpatialSelector {

	public static boolean centered = false;

	private SpatialSelector() {
	}

	public static Geometry getGeometry(Node n) {
		Ray r;
		if (centered) {
			r = getCameraRay();
		} else {
			r = getMouseRay();
		}
		return PointUtil.getPointedGeometry(n, r);
	}

	public static Point2D getCoord(Node n) {
		Ray r;
		if (centered) {
			r = getCameraRay();
		} else {
			r = getMouseRay();
		}
		return PointUtil.getPointedCoord(n, r);
	}

	public static Point2D getCenterViewCoord(Node n) {
		return PointUtil.getPointedCoord(n, getCameraRay());
	}

	public static Point2D getCoord(Node n, Point2D screenCoord) {
		Vector3f origin = RendererPlatform.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 0f);
		Vector3f direction = RendererPlatform.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 1f);
		direction.subtractLocal(origin).normalizeLocal();
		Ray r = new Ray(origin, direction);
		return PointUtil.getPointedCoord(n, r);
	}

	public static Point3D getPoint(Node n, Point2D screenCoord) {
		Vector3f origin = RendererPlatform.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 0f);
		Vector3f direction = RendererPlatform.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 1f);
		direction.subtractLocal(origin).normalizeLocal();
		Ray r = new Ray(origin, direction);
		return PointUtil.getPointedCoord3D(n, r);
	}

	public static Geometry getPointedGeometry(Node n, Point2D screenCoord) {
		Vector3f origin = RendererPlatform.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 0f);
		Vector3f direction = RendererPlatform.getCamera().getWorldCoordinates(TranslateUtil.toVector2f(screenCoord), 1f);
		direction.subtractLocal(origin).normalizeLocal();
		Ray r = new Ray(origin, direction);
		return PointUtil.getPointedGeometry(n, r);
	}

	public static Point2D getScreenCoord(Point3D pos) {
		Vector3f vPos = TranslateUtil.toVector3f(pos);
		Vector3f screenCoord = RendererPlatform.getCamera().getScreenCoordinates(vPos);
		return TranslateUtil.toPoint3D(screenCoord).get2D();
	}

	private static Ray getMouseRay() {
		Vector3f origin = RendererPlatform.getCamera().getWorldCoordinates(RendererPlatform.getInputManager().getCursorPosition(), 0f);
		Vector3f direction = RendererPlatform.getCamera().getWorldCoordinates(RendererPlatform.getInputManager().getCursorPosition(), 1f);
		direction.subtractLocal(origin).normalizeLocal();
		return new Ray(origin, direction);
	}

	private static Ray getCameraRay() {
		return new Ray(RendererPlatform.getCamera().getLocation(), RendererPlatform.getCamera().getDirection());
	}
}
