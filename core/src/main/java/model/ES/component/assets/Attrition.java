package model.ES.component.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Attrition implements EntityComponent {
	public final int maxHitpoints, actualHitpoints;
	private final String spawnOnDeath;
	
	public Attrition() {
		maxHitpoints = 0;
		actualHitpoints = 0;
		spawnOnDeath = null;
	}

	public Attrition(@JsonProperty("maxHitpoints")int maxHitpoints,
			@JsonProperty("actualHitpoints")int actualHitpoints,
			@JsonProperty("spawnOnDeath")String spawnOnDeath) {
		this.maxHitpoints = maxHitpoints;
		this.actualHitpoints = actualHitpoints;
		this.spawnOnDeath = spawnOnDeath;
	}

	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	public int getActualHitpoints() {
		return actualHitpoints;
	}

	public String getSpawnOnDeath() {
		return spawnOnDeath;
	}
}
