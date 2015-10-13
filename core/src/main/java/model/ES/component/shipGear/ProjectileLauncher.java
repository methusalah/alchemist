package model.ES.component.shipGear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.math.Fraction;

public class ProjectileLauncher implements EntityComponent{
	private final Fraction precision;
	
	public ProjectileLauncher(@JsonProperty("precision")Fraction precision) {
		this.precision = precision;
	}

	public Fraction getPrecision() {
		return precision;
	}
}
