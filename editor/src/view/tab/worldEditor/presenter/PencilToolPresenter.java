package view.tab.worldEditor.presenter;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import presentation.ToggledEnumProperty;
import util.geometry.collections.PointRing;
import util.geometry.geom2d.BoundingCircle;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Polygon;
import util.geometry.geom2d.algorithm.PerlinNoise;
import util.math.AngleUtil;


public class PencilToolPresenter extends WorldTool {
	public static final int MAX_SIZE = 30;

	public enum Shape {
		SQUARE, DIAMOND, CIRCLE
	}

	public enum Mode {
		ROUGH, AIRBRUSH, NOISE
	}

	private final PerlinNoise perlin;

	private final ToggledEnumProperty<Shape> shapeProperty = new ToggledEnumProperty<>(Shape.class);
	private final ToggledEnumProperty<Mode> modeProperty = new ToggledEnumProperty<>(Mode.class);
	
	private final IntegerProperty sizeProperty = new SimpleIntegerProperty(4);
	private final DoubleProperty strengthProperty = new SimpleDoubleProperty(0.1);

	public PencilToolPresenter() {
		perlin = new PerlinNoise();
	}
	
	private List<Point2D> getInCircle() {
		List<Point2D> res = new ArrayList<>();
		BoundingCircle circle = new BoundingCircle(coord, (sizeProperty.getValue() / 2) + 0.01);

		for (int x = -(int) sizeProperty.getValue(); x < (int) sizeProperty.getValue(); x++) {
			for (int y = -(int) sizeProperty.getValue(); y < (int) sizeProperty.getValue(); y++) {
				Point2D p = coord.getAddition(x, y);
				if (circle.contains(p)) {
					res.add(p);
				}
			}
		}
		return res;
	}

	private List<Point2D> getInQuad() {
		List<Point2D> res = new ArrayList<>();
		Polygon quad = getOrientedQuad(coord);

		for (int x = -(int) sizeProperty.getValue(); x < (int) sizeProperty.getValue(); x++) {
			for (int y = -(int) sizeProperty.getValue(); y < (int) sizeProperty.getValue(); y++) {
				Point2D p = coord.getAddition(x, y);
				if (quad.hasInside(p)) {
					res.add(p);
				}
			}
		}
		return res;
	}

	private Polygon getOrientedQuad(Point2D center) {
		PointRing pr = new PointRing();
		double halfSide = (sizeProperty.getValue() / 2) - 0.01;
		pr.add(center.getAddition(-halfSide, -halfSide));
		pr.add(center.getAddition(halfSide, -halfSide));
		pr.add(center.getAddition(halfSide, halfSide));
		pr.add(center.getAddition(-halfSide, halfSide));
		switch (shapeProperty.getValue()) {
			case SQUARE:
				return new Polygon(pr);
			case DIAMOND:
				return new Polygon(pr).getRotation(AngleUtil.RIGHT / 2, center);
			default:
				throw new RuntimeException();
		}
	}
	
	private double getEccentricity(Point2D p) {
		switch (shapeProperty.getValue()) {
			case SQUARE:
				double xDist = Math.abs(p.x - coord.x);
				double yDist = Math.abs(p.y - coord.y);
				return ((sizeProperty.getValue() / 2) - Math.max(xDist, yDist)) / (sizeProperty.getValue() / 2);
			case DIAMOND:
				xDist = Math.abs(p.x - coord.x);
				yDist = Math.abs(p.y - coord.y);
				return ((sizeProperty.getValue() / 2) * 1.414 - xDist - yDist) / ((sizeProperty.getValue() / 2) * 1.414);
			case CIRCLE:
				return ((sizeProperty.getValue() / 2) - p.getDistance(coord)) / (sizeProperty.getValue() / 2);
		}
		return 0;
	}

	public double getApplicationRatio(Point2D p) {
		switch (modeProperty.getValue()) {
			case ROUGH:
				return 1;
			case AIRBRUSH:
				double x = getEccentricity(p);
				x = x * 10;
				x -= 5;
				double localFalloff = 1 / (1 + Math.exp(-x));
				return localFalloff;
			case NOISE:
				return perlin.noise(p);//, 10, 1);
			default:
				throw new RuntimeException();
		}
	}

	protected List<Point2D> getNodes() {
		if(coord == null)
			return new ArrayList<Point2D>();
		switch(shapeProperty.getValue()){
		case CIRCLE : return getInCircle();
		case DIAMOND :
		case SQUARE : return getInQuad();
		default : throw new RuntimeException();
		}
	}

	public IntegerProperty getSizeProperty() {
		return sizeProperty;
	}

	public DoubleProperty getStrengthProperty() {
		return strengthProperty;
	}

	public static int getMaxSize() {
		return MAX_SIZE;
	}

	public PerlinNoise getPerlin() {
		return perlin;
	}

	public ToggledEnumProperty<Shape> getShapeProperty() {
		return shapeProperty;
	}

	public ToggledEnumProperty<Mode> getModeProperty() {
		return modeProperty;
	}
}
