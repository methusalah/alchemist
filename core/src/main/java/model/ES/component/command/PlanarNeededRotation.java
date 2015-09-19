package model.ES.component.command;

import java.text.DecimalFormat;

import util.math.AngleUtil;

import com.simsilica.es.EntityComponent;


public class PlanarNeededRotation implements EntityComponent {
	private final double angle;
	
	public PlanarNeededRotation(double angle) {
		if(angle == 0)
			throw new IllegalArgumentException("can't need a rotation of 0");
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}


	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return this.getClass().getSimpleName() + " - rotation :"+df.format(AngleUtil.toDegrees(angle));
	}

}
