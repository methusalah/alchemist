package ECS.component.combat.resistance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Shield implements EntityComponent {
	private final int capacity;
	private final int rechargeRate;
	private final int rechargeDelay;
	private final int delay;
	
	public Shield() {
		this.capacity = 0;
		this.rechargeRate = 0;
		this.rechargeDelay = 0;
		this.delay = 0;
	}

	public Shield(@JsonProperty("capacity")int capacity,
			@JsonProperty("rechargeRate")int rechargeRate,
			@JsonProperty("rechargeDelay")int rechargeDelay,
			@JsonProperty("delay")int delay) {
		this.capacity = capacity;
		this.rechargeRate = rechargeRate;
		this.rechargeDelay = rechargeDelay;
		this.delay = delay;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getRechargeRate() {
		return rechargeRate;
	}

	public int getRechargeDelay() {
		return rechargeDelay;
	}

	public int getDelay() {
		return delay;
	}
	
}
