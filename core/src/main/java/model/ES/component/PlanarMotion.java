package model.ES.component;

import util.entity.Comp;

public class PlanarMotion implements Comp{
	private final double desiredOrientation;
	private final double desiredDistance;
	
	
	public PlanarMotion(double desiredDistance, double desiredOrientation) {
		this.desiredDistance = desiredDistance;
		this.desiredOrientation = desiredOrientation;
	}


	public double getDesiredOrientation() {
		return desiredOrientation;
	}


	public double getDesiredDistance() {
		return desiredDistance;
	}
}
