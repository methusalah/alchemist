package ECS.component.motion.physic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class CircleCollisionShape implements EntityComponent {
	private final double radius;
	
	public CircleCollisionShape() {
		radius = 0;
	}

	public CircleCollisionShape(@JsonProperty("radius")double radius) {
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}
}
