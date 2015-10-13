package model.ES.component.shipGear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class RotationThruster implements EntityComponent {
	public final boolean clockwise;
	public final double maxAngle;
	public final double activation;
	public final boolean onOff;
	
	public RotationThruster(@JsonProperty("clockwise")boolean clockwise,
			@JsonProperty("maxAngle")double maxAngle,
			@JsonProperty("activation")double activation,
			@JsonProperty("onOff")boolean onOff) {
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
