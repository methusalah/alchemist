package model.ES.component.shipGear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.LogUtil;

public class Trigger implements EntityComponent {
	public final String name;
	public final boolean triggered;
	
	public Trigger() {
		name = "";
		triggered = false;
	}

	public Trigger(@JsonProperty("name")String name, @JsonProperty("triggered")boolean triggered) {
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
