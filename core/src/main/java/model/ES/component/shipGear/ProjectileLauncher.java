package model.ES.component.shipGear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class ProjectileLauncher implements EntityComponent{
	public final double precision;
	
	public ProjectileLauncher(@JsonProperty("precision")double precision) {
		this.precision = precision;
	}

	public double getPrecision() {
		return precision;
	}
}
