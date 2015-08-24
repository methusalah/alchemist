package model.ES.component.motion;

import util.entity.Comp;

public class PlanarRealisticMotion implements Comp{
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
