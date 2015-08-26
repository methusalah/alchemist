package model.ES.component.motion;

import java.text.DecimalFormat;

import util.math.AngleUtil;

import com.simsilica.es.EntityComponent;


public class PlanarNeededRotation implements EntityComponent {
	private final double rotation;
	
	public PlanarNeededRotation(double rotation) {
		if(rotation == 0)
			throw new IllegalArgumentException("can't need a rotation of 0");
		this.rotation = rotation;
	}

	public double getRotation() {
		return rotation;
	}


	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return this.getClass().getSimpleName() + " - rotation :"+df.format(AngleUtil.toDegrees(rotation));
	}

}
