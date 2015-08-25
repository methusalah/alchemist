package model.ES.component.motion;

import java.text.DecimalFormat;

import util.math.AngleUtil;

import com.simsilica.es.EntityComponent;
import com.sun.javafx.binding.StringFormatter;

public class PlanarIntendedMotion implements EntityComponent {
	private final double rotation;
	private final double distance;
	
	
	public PlanarIntendedMotion(double distance, double rotation) {
		this.distance = distance;
		this.rotation = rotation;
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
