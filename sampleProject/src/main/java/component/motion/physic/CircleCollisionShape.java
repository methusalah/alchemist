package component.motion.physic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class CircleCollisionShape implements EntityComponent {
	private final double radius;
	private final double density;
	private final double restitution;
	
	public CircleCollisionShape() {
		radius = 0;
		density = 0;
		restitution = 0;
	}

	public CircleCollisionShape(@JsonProperty("radius")double radius, 
			@JsonProperty("density")double density,
			@JsonProperty("restitution")double restitution) {
		this.radius = radius;
		this.density = density;
		this.restitution = restitution;
	}

	public double getRadius() {
		return radius;
	}

	public double getDensity() {
		return density;
	}

	public double getRestitution() {
		return restitution;
	}
}
