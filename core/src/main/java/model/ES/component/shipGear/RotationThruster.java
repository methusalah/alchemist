package model.ES.component.shipGear;

import com.simsilica.es.EntityComponent;

public class RotationThruster implements EntityComponent {
	private final boolean clockwise;
	private final double maxAngle;
	private final double activation;
	private final boolean onOff;
	
	public RotationThruster(boolean clockwise, double maxAngle, double activation, boolean onOff) {
		this.clockwise = clockwise;
		this.activation = activation;
		this.maxAngle = maxAngle;
		this.onOff = onOff;
	}

	public boolean isClockwise() {
		return clockwise;
	}

	public double getMaxAngle() {
		return maxAngle;
	}

	public double getActivation() {
		return activation;
	}

	public boolean isOnOff() {
		return onOff;
	}
}
