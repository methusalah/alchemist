package model.ES.component.motion;

import java.text.DecimalFormat;

import util.math.AngleUtil;

import com.simsilica.es.EntityComponent;

public class PlanarMotion implements EntityComponent{
	private final float elapsedTime;
	private final double rotation;
	private final double distance;
	
	
	public PlanarMotion(double distance, double rotation, float elapsedTime) {
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
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return this.getClass().getSimpleName() + " - rotation = "+df.format(AngleUtil.toDegrees(rotation))+"; distance = "+df.format(distance);
	}
}
