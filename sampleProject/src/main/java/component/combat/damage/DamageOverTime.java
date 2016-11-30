package component.combat.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class DamageOverTime implements EntityComponent {
	private final DamageType type;
	private final int amountPerSecond;
	private final int tickPerSecond;
	private final int timeSinceLastTick;
	
	public DamageOverTime() {
		type = DamageType.BASIC;
		amountPerSecond = 0;
		timeSinceLastTick = 0;
		tickPerSecond = 0;
	}
	
	public DamageOverTime(@JsonProperty("type")DamageType type,
			@JsonProperty("amountPerSecond")int amountPerSecond,
			@JsonProperty("tickPerSecond")int tickPerSecond,
			@JsonProperty("timeSinceLastTick")int timeSinceLastTick){
		this.type = type;
		this.amountPerSecond = amountPerSecond;
		this.timeSinceLastTick = timeSinceLastTick;
		this.tickPerSecond = tickPerSecond;
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

	public int getTickPerSecond() {
		return tickPerSecond;
	}
	
}
