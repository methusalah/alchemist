package model.ES.component.camera;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class ChasingCamera implements EntityComponent {
	public final double maxSpeed;
	public final double speed;
	public final double acceleration;
	public final double deceleration;
	
	public ChasingCamera(@JsonProperty("maxSpeed")double maxSpeed,
			@JsonProperty("speed")double speed,
			@JsonProperty("acceleration")double acceleration,
			@JsonProperty("deceleration")double deceleration) {
		this.maxSpeed = maxSpeed;
		this.speed = speed;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public double getSpeed() {
		return speed;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public double getDeceleration() {
		return deceleration;
	}
	
}
