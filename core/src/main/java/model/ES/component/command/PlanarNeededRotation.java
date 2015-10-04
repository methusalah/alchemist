package model.ES.component.command;

import java.text.DecimalFormat;

import util.math.AngleUtil;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;


public class PlanarNeededRotation implements EntityComponent {
	public final double angle;
	
	public PlanarNeededRotation(@JsonProperty("angle")double angle) {
		if(angle == 0)
			throw new IllegalArgumentException("can't need a rotation of 0");
		this.angle = angle;
	}
}
