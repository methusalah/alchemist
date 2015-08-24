package model.ES.component.motion;

import com.simsilica.es.EntityComponent;

public class PlanarInertia implements EntityComponent {
	private final double speed;
	
	public double getSpeed() {
		return speed;
	}

	public PlanarInertia(double speed) {
		this.speed = speed;
	}
	
	
	
	
}
