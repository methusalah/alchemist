package model.ES.component.behavior;

import com.simsilica.es.EntityComponent;

public class SpawnOnDeath implements EntityComponent {
	private final String blueprint;

	public SpawnOnDeath() {
		blueprint = "";
	}
	
	public SpawnOnDeath(String blueprint) {
		this.blueprint = blueprint;
	}

	public String getBlueprint() {
		return blueprint;
	}
	
}
