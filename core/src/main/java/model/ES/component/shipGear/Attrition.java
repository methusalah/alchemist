package model.ES.component.shipGear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Attrition implements EntityComponent {
	public final int maxHitpoints, actualHitpoints;
	
	public Attrition(@JsonProperty("maxHitpoints")int maxHitpoints, @JsonProperty("actualHitpoints")int actualHitpoints) {
		this.maxHitpoints = maxHitpoints;
		this.actualHitpoints = actualHitpoints;
	}
}
