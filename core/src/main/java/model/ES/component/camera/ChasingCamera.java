package model.ES.component.camera;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class ChasingCamera implements EntityComponent {
	private final EntityId entityToChase;
	private final double maxSpeed;
	private final double speed;
	private final double acceleration;
	private final double deceleration;
	
	public ChasingCamera(EntityId entityToChase, double maxSpeed, double speed, double acceleration, double deceleration) {
		this.entityToChase = entityToChase;
		this.maxSpeed = maxSpeed;
		this.speed = speed;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public double getDeceleration() {
		return deceleration;
	}

	public EntityId getEntityToChase() {
		return entityToChase;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public double getSpeed() {
		return speed;
	}
	
}
