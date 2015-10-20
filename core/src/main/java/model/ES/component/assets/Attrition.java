package model.ES.component.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Attrition implements EntityComponent {
	public final int maxHitpoints, actualHitpoints;
	
	public Attrition() {
		maxHitpoints = 0;
		actualHitpoints = 0;
	}

	public Attrition(@JsonProperty("maxHitpoints")int maxHitpoints, @JsonProperty("actualHitpoints")int actualHitpoints) {
		this.maxHitpoints = maxHitpoints;
		this.actualHitpoints = actualHitpoints;
	}

	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	public int getActualHitpoints() {
		return actualHitpoints;
	}
}
