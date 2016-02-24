package ECS.component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class ThrusterControl implements EntityComponent {
	private final boolean active;
	
	public ThrusterControl() {
		active = true;
	}

	public ThrusterControl(@JsonProperty("active")boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
}
