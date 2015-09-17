package model.ES.component.physic;

import com.simsilica.es.EntityComponent;

public class Impulse implements EntityComponent {
	private final double range;
	private final double radius;
	private final double force;
	
	private Impulse(double range, double radius, double force) {
		this.range = range;
		this.radius = radius;
		this.force = force;
	}

	public double getRange() {
		return range;
	}

	public double getRadius() {
		return radius;
	}

	public double getForce() {
		return force;
	}
	
}
