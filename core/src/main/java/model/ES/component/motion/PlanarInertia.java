package model.ES.component.motion;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class PlanarInertia implements EntityComponent {
	private final Point2D velocity;
	private final Point2D appliedVelocity;
	
	public PlanarInertia(Point2D velocity) {
		this(velocity, Point2D.ORIGIN);
	}

	public PlanarInertia(Point2D velocity, Point2D appliedVelocity) {
		this.velocity = velocity;
		this.appliedVelocity = appliedVelocity;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public Point2D getAppliedVelocity() {
		return appliedVelocity;
	}
}
