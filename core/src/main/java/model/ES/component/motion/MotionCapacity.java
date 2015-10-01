package model.ES.component.motion;

import com.simsilica.es.EntityComponent;

public class MotionCapacity implements EntityComponent {
	public final double maxSpeed;
	public final double maxRotationSpeed;
	public final double thrustPower;
	public final double lateralThrustPower;
	public final double frontalThrustPower;
	
	public MotionCapacity(double maxSpeed,
			double maxRotationSpeed,
			double thrustPower,
			double lateralThrustPower,
			double frontalThrustPower){
		this.maxRotationSpeed = maxRotationSpeed;
		this.maxSpeed = maxSpeed;
		this.thrustPower = thrustPower;
		this.lateralThrustPower = lateralThrustPower;
		this.frontalThrustPower = frontalThrustPower;
	}
}
