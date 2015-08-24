package model.ES.component.motion;

import com.simsilica.es.EntityComponent;

public class PlanarDesiredMotion implements EntityComponent {
	private final float elapsedTime;
	private final double rotation;
	private final double distance;
	
	
	public PlanarDesiredMotion(double distance, double rotation, float elapsedTime) {
		this.distance = distance;
		this.rotation = rotation;
		this.elapsedTime = elapsedTime;
	}


	public float getElapsedTime() {
		return elapsedTime;
	}


	public double getRotation() {
		return rotation;
	}


	public double getDistance() {
		return distance;
	}
}
