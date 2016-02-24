package ECS.component.combat.resistance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Attrition implements EntityComponent {
	private final int maxHitpoints, actualHitpoints;
	private final int maxShield, actualShield;
	private final boolean armored;
	
	public Attrition() {
		maxHitpoints = 0;
		actualHitpoints = 0;
		
		maxShield = 0;
		actualShield = 0;
		
		armored = false;
	}

	public Attrition(@JsonProperty("maxHitpoints")int maxHitpoints,
			@JsonProperty("actualHitpoints")int actualHitpoints,
			@JsonProperty("maxShield")int maxShield,
			@JsonProperty("actualShield")int actualShield,
			@JsonProperty("armored")boolean armored) {
		this.maxHitpoints = maxHitpoints;
		this.actualHitpoints = actualHitpoints;
		this.maxShield = maxShield;
		this.actualShield = actualShield;
		this.armored = armored;
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
		return armored;
	}
}
