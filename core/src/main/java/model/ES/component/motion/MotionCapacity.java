package model.ES.component.motion;

import com.simsilica.es.EntityComponent;

public class MotionCapacity implements EntityComponent {
	private final double maxSpeed;
	private final double maxRotationSpeed;
	private final double thrustPower;
	
	public MotionCapacity(double maxSpeed,
			double maxRotationSpeed,
			double thrustPower){
		this.maxRotationSpeed = maxRotationSpeed;
		this.maxSpeed = maxSpeed;
		this.thrustPower = thrustPower;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public double getMaxRotationSpeed() {
		return maxRotationSpeed;
	}

	public double getThrustPower() {
		return thrustPower;
	}
}
