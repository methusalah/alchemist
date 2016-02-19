package component.ability;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class AbilityTriggerControl implements EntityComponent {
	private final boolean active;
	
	public AbilityTriggerControl() {
		active = true;
	}

	public AbilityTriggerControl(@JsonProperty("active")boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}	
}
