package model.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.world.terrain.heightmap.Height;
import util.geometry.collections.PointRing;
import util.geometry.geom2d.BoundingCircle;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Polygon;
import util.geometry.geom2d.algorithm.PerlinNoise;
import util.math.AngleUtil;


public class PencilTool extends Tool {
	public static final int MAX_SIZE = 12;

	public enum SHAPE {
		Square, Diamond, Circle
	}

	public enum MODE {
		Rough, Airbrush, Noise
	}

	private final PerlinNoise perlin;

	private SHAPE shape = SHAPE.Square;
	private MODE mode = MODE.Rough;
	private double size = 4;
	private double strength = 1;
	
	public PencilTool(WorldData world) {
		super(world);
		perlin = new PerlinNoise();
	}

	public SHAPE getShape() {
		return shape;
	}

	public void setShape(SHAPE shape) {
		this.shape = shape;
	}

	public MODE getMode() {
		return mode;
	}

	public void setMode(MODE mode) {
		this.mode = mode;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}
	
	protected Map<Height, Point2D> getHeights() {
		switch(shape){
		case Circle : return getInCircle();
		case Diamond :
		case Square : return getInQuad();
		default : throw new RuntimeException();
		}
	}
		
	
	private Map<Height, Point2D> getInCircle() {
		Map<Height, Point2D> res = new HashMap<>();
		BoundingCircle circle = new BoundingCircle(coord, (size / 2) + 0.01);

		for (int x = -(int) size; x < (int) size; x++) {
			for (int y = -(int) size; y < (int) size; y++) {
				Point2D p = new Point2D(x, y).getAddition(coord);
				if (circle.contains(p)) {
					res.put(world.getRegion(p).getTerrain().getHeightMap().get(p), p);
				}
			}
		}
		return res;
	}

	private Map<Height, Point2D> getInQuad() {
		Map<Height, Point2D> res = new HashMap<>();
		Polygon quad = getOrientedQuad(coord);

		for (int x = -(int) size; x < (int) size; x++) {
			for (int y = -(int) size; y < (int) size; y++) {
				Point2D p = new Point2D(x, y).getAddition(coord);
				if (quad.hasInside(p)) {
					res.put(world.getRegion(p).getTerrain().getHeightMap().get(p), p);
				}
			}
		}
		return res;
	}

	private Polygon getOrientedQuad(Point2D center) {
		PointRing pr = new PointRing();
		double halfSide = (size / 2) - 0.01;
		pr.add(center.getAddition(-halfSide, -halfSide));
		pr.add(center.getAddition(halfSide, -halfSide));
		pr.add(center.getAddition(halfSide, halfSide));
		pr.add(center.getAddition(-halfSide, halfSide));
		switch (shape) {
			case Square:
				return new Polygon(pr);
			case Diamond:
				return new Polygon(pr).getRotation(AngleUtil.RIGHT / 2, center);
			default:
				throw new RuntimeException();
		}
	}
	
	private double getEccentricity(Point2D p) {
		switch (shape) {
			case Square:
				double xDist = Math.abs(p.x - coord.x);
				double yDist = Math.abs(p.y - coord.y);
				return ((size / 2) - Math.max(xDist, yDist)) / (size / 2);
			case Diamond:
				xDist = Math.abs(p.x - coord.x);
				yDist = Math.abs(p.y - coord.y);
				return ((size / 2) * 1.414 - xDist - yDist) / ((size / 2) * 1.414);
			case Circle:
				return ((size / 2) - p.getDistance(coord)) / (size / 2);
		}
		return 0;
	}

	public double getApplicationRatio(Point2D p) {
		switch (mode) {
			case Rough:
				return 1;
			case Airbrush:
				double x = getEccentricity(p);
				x = x * 10;
				x -= 5;
				double localFalloff = 1 / (1 + Math.exp(-x));
				return localFalloff;
			case Noise:
				return perlin.noise(p);//, 10, 1);
			default:
				throw new RuntimeException();
		}
	}
	
}
