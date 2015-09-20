package model.ES.component.motion.physic;

import com.simsilica.es.EntityComponent;

public class PhysicForce implements EntityComponent {
	private final double range;
	private final double radius;
	private final double force;
	
	public PhysicForce(double range, double radius, double force) {
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
