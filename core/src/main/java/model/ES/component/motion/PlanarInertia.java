package model.ES.component.motion;

import util.entity.Comp;

public class PlanarInertia implements Comp {
	private final double speed;
	
	public double getSpeed() {
		return speed;
	}

	public PlanarInertia(double speed) {
		this.speed = speed;
	}
	
	
	
	
}
