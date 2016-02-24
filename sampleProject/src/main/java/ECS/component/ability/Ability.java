package ECS.component.ability;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Ability implements EntityComponent {
	private final String name;
	private final boolean triggered;
	
	public Ability() {
		name = "";
		triggered = false;
	}

	public Ability(@JsonProperty("name")String name, @JsonProperty("triggered")boolean triggered) {
		this.name = name;
		this.triggered = triggered;
	}

	public String getName() {
		return name;
	}

	public boolean isTriggered() {
		return triggered;
	}
}
