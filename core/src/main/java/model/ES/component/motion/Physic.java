package model.ES.component.motion;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

import model.ES.component.motion.collision.CollisionShape;

public class Physic implements EntityComponent {
	private final CollisionShape shape;
	private final double restitution;
	private final double mass;
	private final Point2D velocity;
	
	public Physic(Point2D velocity, double mass, CollisionShape shape, double restitution) {
		this.velocity = velocity;
		this.mass = mass;
		this.shape = shape;
		this.restitution = restitution;
	}

	public double getMass() {
		return mass;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public CollisionShape getShape() {
		return shape;
	}

	public double getRestitution() {
		return restitution;
	}
}
