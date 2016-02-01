package model.ES.component.assets.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class DamageOverTime implements EntityComponent {
	private final DamageType type;
	private final int amountPerSecond;
	private final int durationMillisecond;
	
	public DamageOverTime() {
		type = DamageType.BASIC;
		amountPerSecond = 0;
		durationMillisecond = 0;
	}
	
	public DamageOverTime(@JsonProperty("type")DamageType type,
			@JsonProperty("amountPerSecond")int amountPerSecond,
			@JsonProperty("durationMillisecond")int durationMillisecond){
		this.type = type;
		this.amountPerSecond = amountPerSecond;
		this.durationMillisecond = durationMillisecond;
	}

	
	public DamageType getType() {
		return type;
	}

	public int getAmountPerSecond() {
		return amountPerSecond;
	}

	public int getDurationMillisecond() {
		return durationMillisecond;
	}
}
