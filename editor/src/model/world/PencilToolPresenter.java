package model.world;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.world.HeightMapToolPresenter.Operation;
import util.geometry.collections.PointRing;
import util.geometry.geom2d.BoundingCircle;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Polygon;
import util.geometry.geom2d.algorithm.PerlinNoise;
import util.math.AngleUtil;


public class PencilToolPresenter extends WorldTool {
	public static final int MAX_SIZE = 12;

	public enum Shape {
		SQUARE, DIAMOND, CIRCLE
	}

	public enum Mode {
		ROUGH, AIRBRUSH, NOISE
	}

	private final PerlinNoise perlin;

	protected final ObjectProperty<Shape> shapeProperty = new SimpleObjectProperty<>(Shape.SQUARE);
	private final BooleanProperty squareProperty = new SimpleBooleanProperty();
	private final BooleanProperty diamondProperty = new SimpleBooleanProperty();
	private final BooleanProperty circleProperty = new SimpleBooleanProperty();

	protected final ObjectProperty<Mode> modeProperty = new SimpleObjectProperty<>(Mode.ROUGH);
	private final BooleanProperty roughProperty = new SimpleBooleanProperty();
	private final BooleanProperty airbrushProperty = new SimpleBooleanProperty();
	private final BooleanProperty noiseProperty = new SimpleBooleanProperty();
	
	private final IntegerProperty sizeProperty = new SimpleIntegerProperty(4);
	private final DoubleProperty strengthProperty = new SimpleDoubleProperty(0.1);

	public PencilToolPresenter(WorldData world) {
		super(world);
		perlin = new PerlinNoise();
		squareProperty.addListener((observable, oldValue, newValue) -> {
			if(newValue)
				shapeProperty.setValue(Shape.SQUARE);
		});
		diamondProperty.addListener((observable, oldValue, newValue) -> {
			if(newValue)
				shapeProperty.setValue(Shape.DIAMOND);
		});
		circleProperty.addListener((observable, oldValue, newValue) -> {
			if(newValue)
				shapeProperty.setValue(Shape.CIRCLE);
		});
		circleProperty.setValue(true);

		
		roughProperty.addListener((observable, oldValue, newValue) -> {
			if(newValue)
				modeProperty.setValue(Mode.ROUGH);
		});
		airbrushProperty.addListener((observable, oldValue, newValue) -> {
			if(newValue)
				modeProperty.setValue(Mode.AIRBRUSH);
		});
		noiseProperty.addListener((observable, oldValue, newValue) -> {
			if(newValue)
				modeProperty.setValue(Mode.NOISE);
		});
		airbrushProperty.setValue(true);
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

	public BooleanProperty getSquareProperty() {
		return squareProperty;
	}

	public BooleanProperty getDiamondProperty() {
		return diamondProperty;
	}

	public BooleanProperty getCircleProperty() {
		return circleProperty;
	}

	public BooleanProperty getRoughProperty() {
		return roughProperty;
	}

	public BooleanProperty getAirbrushProperty() {
		return airbrushProperty;
	}

	public BooleanProperty getNoiseProperty() {
		return noiseProperty;
	}
	
	
}
