package component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class RandomVelocityToApply implements EntityComponent {
	private final double force;
	private final double forceRange;
	
	public RandomVelocityToApply() {
		force = 0;
		forceRange = 0;
	}

	public RandomVelocityToApply(@JsonProperty("force")double force,
			@JsonProperty("forceRange")double forceRange) {
		this.force = force;
		this.forceRange = forceRange;
	}

	public double getForce() {
		return force;
	}

	public double getForceRange() {
		return forceRange;
	}
}
