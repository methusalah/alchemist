package ECS.component.combat.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

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
