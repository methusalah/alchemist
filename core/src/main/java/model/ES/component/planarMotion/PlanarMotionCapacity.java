package model.ES.component.planarMotion;

import com.simsilica.es.EntityComponent;

public class PlanarMotionCapacity implements EntityComponent {
	private final double maxSpeed;
	private final double maxRotationSpeed;
	private final double thrustPower;
	private final double mass;
	
	public PlanarMotionCapacity(double maxSpeed,
			double maxRotationSpeed,
			double thrustPower,
			double mass){
		this.maxRotationSpeed = maxRotationSpeed;
		this.maxSpeed = maxSpeed;
		this.thrustPower = thrustPower;
		this.mass = mass;
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

	public double getMass() {
		return mass;
	}

}
