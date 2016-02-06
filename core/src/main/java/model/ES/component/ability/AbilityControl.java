package model.ES.component.ability;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class AbilityControl implements EntityComponent {
	private final boolean active;
	
	public AbilityControl() {
		active = true;
	}

	public AbilityControl(@JsonProperty("active")boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
}
