package model.ES.component.motion;

import com.simsilica.es.EntityComponent;

public class PlanarInertia implements EntityComponent {
	private final double speed;
	private final boolean accelerate;
	
	public PlanarInertia(double speed, boolean accelerate) {
		this.speed = speed;
		this.accelerate = accelerate;
	}
	
	public boolean isAccelerate() {
		return accelerate;
	}

	public double getSpeed() {
		return speed;
	}

	
	
	
}
