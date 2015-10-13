package model.ES.component.command;

import util.LogUtil;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;


public class PlanarNeededRotation implements EntityComponent {
	public final double angle;
	
	public PlanarNeededRotation(@JsonProperty("angle")double angle) {
		LogUtil.warning("You should not ask for a null rotation.");
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}
}
