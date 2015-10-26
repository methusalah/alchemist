package model.ES.component.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.math.Fraction;

public class ProjectileLauncher implements EntityComponent{
	private final Fraction precision;
	private final String projectileBluePrint;
	
	public ProjectileLauncher() {
		precision = new Fraction(0);
		projectileBluePrint = "";
	}
	public ProjectileLauncher(@JsonProperty("precision")Fraction precision,
			@JsonProperty("projectileBluePrint")String projectileBluePrint) {
		this.precision = precision;
		this.projectileBluePrint = projectileBluePrint;
	}

	public Fraction getPrecision() {
		return precision;
	}
	public String getProjectileBluePrint() {
		return projectileBluePrint;
	}
	
}
