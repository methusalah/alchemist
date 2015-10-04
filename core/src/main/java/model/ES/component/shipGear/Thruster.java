package model.ES.component.shipGear;

import util.geometry.geom3d.Point3D;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Thruster implements EntityComponent {
	public final Point3D direction;
	public final double activationAngle;
	public final double activation;
	public final boolean onOff;
	
	public Thruster(@JsonProperty("direction")Point3D direction,
			@JsonProperty("activationAngle")double activationAngle,
			@JsonProperty("activation")double activation,
			@JsonProperty("onOff")boolean onOff) {
		this.direction = direction;
		this.activation = activation;
		this.activationAngle = activationAngle;
		this.onOff = onOff;
	}
}
