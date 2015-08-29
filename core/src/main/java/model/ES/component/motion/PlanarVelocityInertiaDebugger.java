package model.ES.component.motion;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class PlanarVelocityInertiaDebugger implements EntityComponent {
	private final Point2D velocity;
	private final Point2D appliedVelocity;
	private final Point2D resultingVelocity;
	
	
	public PlanarVelocityInertiaDebugger(Point2D velocity, Point2D appliedVelocity, Point2D resultingVelocity) {
		this.velocity = velocity;
		this.appliedVelocity = appliedVelocity;
		this.resultingVelocity = resultingVelocity;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public Point2D getAppliedVelocity() {
		return appliedVelocity;
	}

	public Point2D getResultingVelocity() {
		return resultingVelocity;
	}
	
	
}
