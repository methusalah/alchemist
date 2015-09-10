package model.ES.component.planarMotion;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class PlanarWipping implements EntityComponent {
	private final double dragging;
	private final Point2D velocity;
	private final Point2D appliedVelocity;
	
	public PlanarWipping(Point2D velocity, double dragging) {
		this(velocity, Point2D.ORIGIN, dragging);
	}

	public PlanarWipping(Point2D velocity, Point2D appliedVelocity, double dragging) {
		this.velocity = velocity;
		this.appliedVelocity = appliedVelocity;
		this.dragging = dragging;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public Point2D getAppliedVelocity() {
		return appliedVelocity;
	}

	public double getDragging() {
		return dragging;
	}
}
