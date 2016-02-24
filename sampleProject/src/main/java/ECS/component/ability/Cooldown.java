package ECS.component.ability;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Cooldown implements EntityComponent{
	private final int remaining;
	private final int duration;
	
	public Cooldown() {
		remaining = 0;
		duration = 0;
	}
	
	public Cooldown(@JsonProperty("remaining")int remaining, @JsonProperty("duration")int duration) {
		this.remaining = remaining;
		this.duration = duration;
	}

	public int getRemaining() {
		return remaining;
	}

	public int getDuration() {
		return duration;
	}
}
