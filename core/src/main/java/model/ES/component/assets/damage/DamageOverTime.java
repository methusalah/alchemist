package model.ES.component.assets.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class DamageOverTime implements EntityComponent {
	private final DamageType type;
	private final int amountPerSecond;
	private final int timeSinceLastTick;
	
	public DamageOverTime() {
		type = DamageType.BASIC;
		amountPerSecond = 0;
		timeSinceLastTick = 0;
	}
	
	public DamageOverTime(@JsonProperty("type")DamageType type,
			@JsonProperty("amountPerSecond")int amountPerSecond){
		this.type = type;
		this.amountPerSecond = amountPerSecond;
		timeSinceLastTick = 0;
	}

	public DamageType getType() {
		return type;
	}

	public int getAmountPerSecond() {
		return amountPerSecond;
	}

	public int getTimeSinceLastTick() {
		return timeSinceLastTick;
	}
	
}
