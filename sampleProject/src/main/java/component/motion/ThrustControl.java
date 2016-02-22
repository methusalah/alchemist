package component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class ThrustControl implements EntityComponent {
	private final boolean active;
	
	public ThrustControl() {
		active = true;
	}

	public ThrustControl(@JsonProperty("active")boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
}
