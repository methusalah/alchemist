package component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.Fraction;

public class Thruster implements EntityComponent {
	public final Point3D direction;
	public final Angle activationAngle;
	public final Fraction activation;
	public final boolean onOff;
	
	public Thruster() {
		direction = Point3D.ORIGIN;
		activationAngle = new Angle(0);
		activation = new Fraction(0);
		onOff = true;
	}
	
	public Thruster(@JsonProperty("direction")Point3D direction,
			@JsonProperty("activationAngle")Angle activationAngle,
			@JsonProperty("activation")Fraction activation,
			@JsonProperty("onOff")boolean onOff) {
		this.direction = direction;
		this.activation = activation;
		this.activationAngle = activationAngle;
		this.onOff = onOff;
	}

	public Point3D getDirection() {
		return direction;
	}

	public Angle getActivationAngle() {
		return activationAngle;
	}

	public Fraction getActivation() {
		return activation;
	}

	public boolean isOnOff() {
		return onOff;
	}
}
