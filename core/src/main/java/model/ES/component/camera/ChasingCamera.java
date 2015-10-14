package model.ES.component.camera;

import util.geometry.geom3d.Point3D;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class ChasingCamera implements EntityComponent {
	public final double maxSpeed;
	public final double speed;
	public final double acceleration;
	public final double deceleration;
	public final Point3D pos;
	public final Point3D target;
	
	public ChasingCamera(@JsonProperty("maxSpeed")double maxSpeed,
			@JsonProperty("speed")double speed,
			@JsonProperty("acceleration")double acceleration,
			@JsonProperty("deceleration")double deceleration,
			@JsonProperty("pos")Point3D pos,
			@JsonProperty("target")Point3D target) {
		this.maxSpeed = maxSpeed;
		this.speed = speed;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.pos = pos;
		this.target = target;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public double getSpeed() {
		return speed;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public double getDeceleration() {
		return deceleration;
	}
	
}
