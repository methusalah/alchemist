package ECS.component.motion;

import com.simsilica.es.EntityComponent;

public class ShockwaveOnTouch implements EntityComponent {
	private final double duration, radius, force;
	
	public ShockwaveOnTouch() {
		duration = 0;
		radius = 0;
		force = 0;
	}
	
	public ShockwaveOnTouch(double duration, double radius, double force) {
		this.duration = duration;
		this.radius = radius;
		this.force = force;
	}

	public double getDuration() {
		return duration;
	}

	public double getRadius() {
		return radius;
	}

	public double getForce() {
		return force;
	}
	
	
}
