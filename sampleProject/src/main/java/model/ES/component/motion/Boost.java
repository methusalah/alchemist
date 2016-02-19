package model.ES.component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.math.Fraction;

public class Boost implements EntityComponent{
	private final double force;
	
	public Boost() {
		force = 0;
	}
	public Boost(@JsonProperty("force")double force) {
		this.force = force;
	}

	public double getForce() {
		return force;
	}
}
