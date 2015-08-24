package model.ES.component.motion;

import com.simsilica.es.EntityComponent;

public class PlanarPossibleMotion implements EntityComponent{
	private final float elapsedTime;
	private final double rotation;
	private final double distance;
	
	
	public PlanarPossibleMotion(double distance, double rotation, float elapsedTime) {
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
