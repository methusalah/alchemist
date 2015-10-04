package model.ES.component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class MotionCapacity implements EntityComponent {
	public final double maxSpeed;
	public final double maxRotationSpeed;
	public final double thrustPower;
	public final double lateralThrustPower;
	public final double frontalThrustPower;
	
	public MotionCapacity(@JsonProperty("maxSpeed")double maxSpeed,
			@JsonProperty("maxRotationSpeed")double maxRotationSpeed,
			@JsonProperty("thrustPower")double thrustPower,
			@JsonProperty("lateralThrustPower")double lateralThrustPower,
			@JsonProperty("frontalThrustPower")double frontalThrustPower){
		this.maxRotationSpeed = maxRotationSpeed;
		this.maxSpeed = maxSpeed;
		this.thrustPower = thrustPower;
		this.lateralThrustPower = lateralThrustPower;
		this.frontalThrustPower = frontalThrustPower;
	}
}
