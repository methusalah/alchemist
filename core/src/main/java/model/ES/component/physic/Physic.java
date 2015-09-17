package model.ES.component.physic;

import model.ES.component.physic.collision.CollisionShape;

import com.simsilica.es.EntityComponent;

public class Physic implements EntityComponent {
	private final CollisionShape shape;
	private final double restitution;
	
	public Physic(CollisionShape shape, double restitution) {
		this.shape = shape;
		this.restitution = restitution;
	}

	public CollisionShape getShape() {
		return shape;
	}

	public double getRestitution() {
		return restitution;
	}
}
