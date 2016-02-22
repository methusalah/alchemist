package component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.math.Fraction;

public class RotationThruster implements EntityComponent {
	public final boolean clockwise;
	public final double maxAngle;
	public final Fraction activation;
	public final boolean onOff;
	
	public RotationThruster() {
		clockwise = false;
		maxAngle = 0;
		activation = new Fraction(0);
		onOff = false;
	}
	
	public RotationThruster(@JsonProperty("clockwise")boolean clockwise,
			@JsonProperty("maxAngle")double maxAngle,
			@JsonProperty("activation")Fraction activation,
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

	public Fraction getActivation() {
		return activation;
	}

	public boolean isOnOff() {
		return onOff;
	}
}
