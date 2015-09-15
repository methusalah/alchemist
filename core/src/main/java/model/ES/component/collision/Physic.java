package model.ES.component.collision;

import java.util.List;

import com.simsilica.es.EntityComponent;

public class Physic implements EntityComponent {
	private final CollisionShape shape;
	private final List<Collision> currentCollisions;
	
	public Physic(CollisionShape shape, List<Collision> currentCollisions) {
		this.shape = shape;
		this.currentCollisions = currentCollisions;
	}

	public CollisionShape getShape() {
		return shape;
	}

	public List<Collision> getCurrentCollisions() {
		return currentCollisions;
	}
	
	public boolean hasCollision(){
		return !currentCollisions.isEmpty();
	}
}
