package model.ES.component.motion;

import com.simsilica.es.EntityComponent;

public class PlanarRealisticMotion implements EntityComponent{
	private final float elapsedTime;
	private final double desiredRotation;
	private final double desiredDistance;
	
	
	public PlanarRealisticMotion(double desiredDistance, double desiredRotation, float elapsedTime) {
		this.desiredDistance = desiredDistance;
		this.desiredRotation = desiredRotation;
		this.elapsedTime = elapsedTime;
	}


	public float getElapsedTime() {
		return elapsedTime;
	}


	public double getDesiredRotation() {
		return desiredRotation;
	}


	public double getDesiredDistance() {
		return desiredDistance;
	}
}
