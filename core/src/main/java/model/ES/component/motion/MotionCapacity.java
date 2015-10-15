package model.ES.component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class MotionCapacity implements EntityComponent {
	public final double maxRotationSpeed;
	public final double thrustPower;
	public final double lateralThrustPower;
	public final double frontalThrustPower;
	
	public MotionCapacity() {
		maxRotationSpeed = 0;
		thrustPower = 0;
		lateralThrustPower = 0;
		frontalThrustPower = 0;
	}
	
	public MotionCapacity(@JsonProperty("maxRotationSpeed")double maxRotationSpeed,
			@JsonProperty("thrustPower")double thrustPower,
			@JsonProperty("lateralThrustPower")double lateralThrustPower,
			@JsonProperty("frontalThrustPower")double frontalThrustPower){
		this.maxRotationSpeed = maxRotationSpeed;
		this.thrustPower = thrustPower;
		this.lateralThrustPower = lateralThrustPower;
		this.frontalThrustPower = frontalThrustPower;
	}

	public double getMaxRotationSpeed() {
		return maxRotationSpeed;
	}

	public double getThrustPower() {
		return thrustPower;
	}

	public double getLateralThrustPower() {
		return lateralThrustPower;
	}

	public double getFrontalThrustPower() {
		return frontalThrustPower;
	}
}
