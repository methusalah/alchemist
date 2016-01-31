package model.ES.component.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Attrition implements EntityComponent {
	private final int maxHitpoints, actualHitpoints;
	private final int maxShield, actualShield;
	private final boolean isArmored;
	
	public Attrition() {
		maxHitpoints = 0;
		actualHitpoints = 0;
		
		maxShield = 0;
		actualShield = 0;
		
		isArmored = false;
	}

	public Attrition(@JsonProperty("maxHitpoints")int maxHitpoints,
			@JsonProperty("actualHitpoints")int actualHitpoints,
			@JsonProperty("maxShield")int maxShield,
			@JsonProperty("actualShield")int actualShield,
			@JsonProperty("isArmored")boolean isArmored) {
		this.maxHitpoints = maxHitpoints;
		this.actualHitpoints = actualHitpoints;
		this.maxShield = maxShield;
		this.actualShield = actualShield;
		this.isArmored = isArmored;
	}

	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	public int getActualHitpoints() {
		return actualHitpoints;
	}

	public int getMaxShield() {
		return maxShield;
	}

	public int getActualShield() {
		return actualShield;
	}

	public boolean isArmored() {
		return isArmored;
	}
}
