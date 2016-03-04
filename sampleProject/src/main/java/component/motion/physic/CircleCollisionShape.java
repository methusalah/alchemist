package component.motion.physic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class CircleCollisionShape implements EntityComponent {
	private final double radius;
	private final double density;
	
	public CircleCollisionShape() {
		radius = 0;
		density = 0;
	}

	public CircleCollisionShape(@JsonProperty("radius")double radius, 
			@JsonProperty("density")double density) {
		this.radius = radius;
		this.density = density;
	}

	public double getRadius() {
		return radius;
	}

	public double getDensity() {
		return density;
	}
}
